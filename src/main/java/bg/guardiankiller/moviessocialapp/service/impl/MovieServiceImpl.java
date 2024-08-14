package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;
import bg.guardiankiller.moviessocialapp.model.entity.MovieEntity;
import bg.guardiankiller.moviessocialapp.repository.MovieRepository;
import bg.guardiankiller.moviessocialapp.service.GenreService;
import bg.guardiankiller.moviessocialapp.service.I18nService;
import bg.guardiankiller.moviessocialapp.service.MovieService;
import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MoviesImportService moviesImportService;
    private final GenreService genreService;
    private final I18nService i18nService;
    private final MovieRepository repository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public void importMovies(int n) {
        var imported = moviesImportService.retrieveNMoviesFromEachGenre(n);
        var genreIds = imported.stream().flatMap(m->m.genres().stream()).distinct().toList();
        var genres = genreService.getEntityByTMIDs(genreIds).stream()
                .collect(Collectors.toMap(GenreEntity::getTmdbId, e->e));

        var entites = imported.stream().map(movie -> {
            var entity = new MovieEntity();
            var releaseDate = movie.release_date() == null ? LocalDate.now() : LocalDate.parse(movie.release_date());
            movie.genres().stream().map(genres::get).forEach(entity::addGenre);

            entity.setTitle(i18nService.addString(movie.title()));
            entity.setOverview(i18nService.addString(movie.overview()));

            entity.setPopularity(movie.popularity());
            entity.setReleaseDate(releaseDate);
            entity.setVoteAverage(movie.vote_average());
            entity.setVoteCount(movie.vote_count());
            return entity;
        }).toList();
        repository.saveAll(entites);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Movie> getAllMovies(Language language, Pageable pageable) {
        var entities = repository.getAllMovies(pageable);
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
                            .forEach(g->dto.getGenres().add(g));
                    dto.setTitle(retrieve(entity.getTitle(), language));
                    dto.setOverview(retrieve(entity.getOverview(), language));
                    return dto;
                });
    }

    private String retrieve(UUID placeholder, Language language) {
        return i18nService
                .retrieve(placeholder, language)
                .orElseGet(()->i18nService.retrieve(placeholder, Language.EN).orElse("N/A"));
    }
}
