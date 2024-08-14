package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.joinpoint.TrackExecutionTime;
import bg.guardiankiller.moviessocialapp.model.dto.*;
import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Genre(int id, String name){}
record Genres(List<Genre> genres){}
record Movie(
        long id,
        List<Integer> genre_ids,
        String title,
        String overview,
        String release_date,
        String poster_path,
        double popularity,
        double vote_average,
        long vote_count
){}
record MoviesPage(
        int page,
        List<Movie> results,
        long total_pages,
        long total_results
){}

record MovieDetails(
        long id,
        List<Genre> genres,
        String title,
        String overview,
        String release_date,
        String poster_path,
        double popularity,
        double vote_average,
        long vote_count
){}
record Cast(
        long id,
        String name,
        double popularity,
        String character,
        String profile_path,
        String known_for_department
) {
}

record Crew(
        long id,
        String name,
        double popularity,
        String job,
        String department
) {
}
record MovieCredits(
        long id,
        List<Cast> cast,
        List<Crew> crew
){
    Cast findCastById(long id) {
        return cast.stream().filter(c->c.id() == id).findFirst().orElseThrow();
    }

    Crew findCrewById(long id) {
        return crew.stream().filter(c->c.id() == id).findFirst().orElseThrow();
    }
}

record Person(
        String birthday,
        int gender,
        long id,
        String name,
        String known_for_department,
        String place_of_birth,
        double popularity,
        String profile_path
) {

}

@Service
@Slf4j
public class MoviesImportServiceImpl implements MoviesImportService {

    private final WebClient apiClient;
    private final WebClient imageClient;

    public MoviesImportServiceImpl(
            @Qualifier("tmdbWebClient") WebClient apiClient,
            @Qualifier("tmdbImagesWebClient") WebClient imageClient) {
        this.apiClient = apiClient;
        this.imageClient = imageClient;
    }

    @Override
    @TrackExecutionTime
    public List<Path> downloadImages(Path temp, String... paths) {
        return downloadImages(temp, Arrays.asList(paths));
    }

    @Override
    @TrackExecutionTime
    public List<Path> downloadImages(Path temp, Collection<String> paths) {
        AtomicInteger count = new AtomicInteger();
        try {
            return Flux.fromIterable(paths)
                    .flatMap(path -> {
                        var dest =  temp.resolve(path.replace("/", ""));
                        return this.getImageMono(path)
                                .flatMap(body->DataBufferUtils.write(Mono.just(body), dest)
                                        .map(x->dest));
                    })
                    .onErrorContinue((error, value)-> {
                        count.incrementAndGet();
                        System.out.println(error.getLocalizedMessage());
                    })
                    .delayElements(Duration.ofMillis(100))
                    .collectList()
                    .block();

        }finally {
            log.warn("Error in {}/{}", count.get(), paths.size());
        }
    }

    @Override
    @TrackExecutionTime
    public Pair<List<TMDBMovie>, List<TMDBPerson>> importNMovies(int n) {
        var start = System.currentTimeMillis();

        var set = Flux
                .fromIterable(getGenres().genres())
                .flatMap(genre -> getMovieIds(genre, n))
                .delayElements(Duration.ofMillis(80))
                .flatMap(this::getMovieById)
                .collectList()
                .block();
        log.info("Found {} movies", set.size());
        var peopleIds = set.stream().flatMap(x -> Stream.concat(
                x.cast().stream().map(CastPerson::id),
                x.crew().stream().map(CrewPerson::id)
        )).collect(Collectors.toSet());
        log.info("Found {} people", peopleIds.size());
        var people = Flux
                .fromIterable(peopleIds)
                .delayElements(Duration.ofMillis(60))
                .flatMap(this::getPerson)
                .collectSortedList(Comparator.comparingLong(Record::hashCode))
                .block();

        log.info("Took: {}s", (System.currentTimeMillis() - start)/1000);
        return new Pair<>(set, people);
    }

    private Mono<TMDBPerson> getPerson(long id) {
        return Mono.zip(
                getPersonByIdMono(id, Language.EN.getCode()),
                getPersonByIdMono(id, Language.BG.getCode()),
                (a,b) -> new TMDBPerson(
                        a.birthday(),
                        a.gender(),
                        a.id(),
                        Map.of(Language.EN, a.name(), Language.BG, b.name()),
                        a.known_for_department(),
                        a.place_of_birth(),
                        a.popularity(),
                        a.profile_path()
                ));
    }

    private Mono<Person> getPersonByIdMono(long id, String lang) {
        var request = apiClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/person/{id}")
                        .queryParam("language", lang)
                        .build(id));
        var response = request.retrieve();
        try {
            return response.bodyToMono(Person.class);
        } catch (WebClientException e) {
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private Mono<TMDBMovie> getMovieById(long id) {
        var mono1 = Mono.zip(
                getMovieById(id, Language.EN),
                getMovieById(id, Language.BG),
                (en, bg) -> Map.of(Language.EN, en, Language.BG, bg));

        return Mono.zip(
                mono1,
                getMovieCredits(id, Language.EN.getCode()),
                (details, credits) -> {
                    var overview = details.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().overview()));
                    var title = details.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().title()));
                    var image = details.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, entry -> Optional.ofNullable(entry.getValue().poster_path()).orElse("None")));

                    var crew = credits.crew().stream()
                            .map(x -> new CrewPerson(x.id(), x.department(), x.job()))
                            .filter(x->"Director".equals(x.job()))
                            .collect(Collectors.toSet());
                    var cast = credits.cast().stream()
                            .map(x -> new CastPerson(x.id(), x.character()))
                            .limit(5)
                            .collect(Collectors.toSet());

                    var en = details.get(Language.EN);
                    var genres = en.genres().stream().map(Genre::id).collect(Collectors.toSet());

                    return new TMDBMovie(
                            en.id(),
                            genres,
                            overview,
                            title,
                            en.release_date(),
                            image,
                            cast, crew,
                            en.popularity(),
                            en.vote_average(),
                            en.vote_count());
                }
        );
    }

    private Genres getGenres() {
        var request = apiClient.get().uri("/genre/movie/list");
        var response = request.retrieve();
        try {
            return response.bodyToMono(Genres.class).block();
        } catch (WebClientException e) {
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<DataBuffer> getImageMono(String path) {
        var request = imageClient.get().uri(path);
        var response = request.retrieve();
        try {
            return response
                    .bodyToMono(DataBuffer.class);
        } catch (WebClientException e) {
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<MovieDetails> getMovieById(long id, Language lang) {
        var request = apiClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("language", lang.getCode())
                        .build(id));
        var response = request.retrieve();
        try {
            return response
                    .bodyToMono(MovieDetails.class);
        } catch (WebClientException e) {
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mono<MovieCredits> getMovieCredits(long id, String lang) {
        var request = apiClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}/credits")
                        .queryParam("language", lang)
                        .build(id));
        var response = request.retrieve();
        try {
            return response.bodyToMono(MovieCredits.class);
        } catch (WebClientException e) {
            log.error("Cannot connect", e);
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Flux<Long> getMovieIds(Genre genre, int n) {
        var pageCount = n / 20 + 1;
        return Flux
                .range(1, pageCount)
                .flatMap(page -> getMoviesPageByGenre(genre, page))
                .flatMap(page ->
                        Flux.merge(page.results().stream()
                                .map(Movie::id)
                                .map(Mono::just)
                                .collect(Collectors.toSet())));
    }

    private Mono<MoviesPage> getMoviesPageByGenre(Genre genre, int page) {
        var request = apiClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("page", page)
                        .queryParam("language", "en-US")
                        .queryParam("sort_by", "popularity.desc")
                        .queryParam("with_genres", genre.id())
                        .build());
        var response = request.retrieve();
        try {
            return response.bodyToMono(MoviesPage.class);
        } catch (WebClientException e) {
            throw new ServerException("Cannot connect to TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
