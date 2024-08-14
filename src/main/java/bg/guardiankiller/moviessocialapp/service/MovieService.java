package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface MovieService {

    void importMovies(int n);

    Page<Movie> getAllMovies(Language language, Pageable pageable);

    Page<Movie> getMoviesByGenreId(long genreId, Language language, Pageable pageable);

    Optional<Movie> getSingleMovie(long id, Language language);
}
