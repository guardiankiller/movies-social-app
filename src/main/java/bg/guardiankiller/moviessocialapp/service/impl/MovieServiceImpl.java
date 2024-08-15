package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;
import bg.guardiankiller.moviessocialapp.model.entity.MovieEntity;
import bg.guardiankiller.moviessocialapp.repository.MovieRepository;
import bg.guardiankiller.moviessocialapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieServiceImpl implements MovieService {

    private final MoviesImportService moviesImportService;
    private final GenreService genreService;
    private final I18nService i18nService;
    private final MovieRepository repository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void importMovies(int n) {
        var imported = moviesImportService.retrieveNMoviesFromEachGenre(n);
        var genreIds = imported.stream().flatMap(m->m.genres().stream()).distinct().toList();
        var genres = genreService.getEntityByTMIDs(genreIds).stream()
                .collect(Collectors.toMap(GenreEntity::getTmdbId, e->e));
        var pictures = imported.stream().flatMap(m->m.poster_path().values().stream()).toList();
        storageService.downloadFromTMDB(pictures);

        var entites = imported.stream().map(movie -> {
            var entity = new MovieEntity();
            var releaseDate = movie.release_date() == null ? LocalDate.now() : LocalDate.parse(movie.release_date());
            movie.genres().stream().map(genres::get).forEach(entity::addGenre);

            entity.setTitle(i18nService.addString(movie.title()));
            entity.setOverview(i18nService.addString(movie.overview()));
            entity.setImagePath(i18nService.addString(movie.poster_path()));

            entity.setPopularity(movie.popularity());
            entity.setReleaseDate(releaseDate);
            entity.setVoteAverage(movie.vote_average());
            entity.setVoteCount(movie.vote_count());
            return entity;
        }).toList();
        repository.saveAll(entites);
    }

    @Override
    public Page<Movie> getAllMovies(Language language, Pageable pageable) {
        var entities = repository.getAllMovies(pageable);
        return toMoviesDTO(language, entities);
    }

    private Page<Movie> toMoviesDTO(Language language, Page<MovieEntity> entities) {
        var genreMap = entities.stream()
                .map(MovieEntity::getGenres)
                .flatMap(Set::stream)
                .distinct()
                .map(e->new Genre(e.getId(), retrieve(e.getName(), language)))
                .collect(Collectors.toMap(Genre::id, g->g));

        return entities
                .map(entity -> {
                    var dto = new Movie();
                    dto.setId(entity.getId());
                    dto.setPopularity(entity.getPopularity());
                    dto.setVoteAverage(entity.getVoteAverage());
                    dto.setVoteCount(entity.getVoteCount());
                    dto.setReleaseDate(entity.getReleaseDate());
                    entity.getGenres().stream()
                            .map(GenreEntity::getId)
                            .map(genreMap::get)
                            .forEach(g -> dto.getGenres().add(g));
                    dto.setTitle(retrieve(entity.getTitle(), language));
                    dto.setOverview(retrieve(entity.getOverview(), language));
                    dto.setImageURL(storageService.retrieveURL(retrieve(entity.getImagePath(), language)));
                    return dto;
                });
    }

    @Override
    public Page<Movie> getMoviesByGenreId(long genreId, Language language, Pageable pageable) {
        var entities = repository.getMoviesByGenreId(genreId, pageable);
        return toMoviesDTO(language, entities);
    }

    @Override
    public Optional<Movie> getSingleMovie(long id, Language language) {
        return repository.findById(id)
                .map(e->toMoviesDTO(language, new PageImpl<>(List.of(e), PageRequest.of(0, 1), 0)))
                .flatMap(p->p.stream().findFirst());
    }

    private String retrieve(UUID placeholder, Language language) {
        return i18nService
                .retrieve(placeholder, language)
                .filter(e->!e.isBlank())
                .orElseGet(()->i18nService.retrieve(placeholder, Language.EN).filter(e->!e.isBlank()).orElse("N/A"));
    }
}
