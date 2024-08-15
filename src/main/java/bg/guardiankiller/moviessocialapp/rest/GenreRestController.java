package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.service.GenreService;
import bg.guardiankiller.moviessocialapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;
    private final MovieService movieService;

    @GetMapping
    public List<Genre> getAllGenres(
            @RequestParam(value = "language", required = false) Language language) {
        language = language == null ? Language.EN : language;
        return genreService.getAllGenres(language);
    }

    @GetMapping("/{id}/movies")
    public Page<Movie> getAllMovies(
            @PathVariable long id,
            @RequestParam(value = "language", required = false) Language language,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "30") int limit) {
        language = language == null ? Language.EN : language;
        return movieService.getMoviesByGenreId(id, language, PageRequest.of(page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getSingleGenre(
            @PathVariable long id,
            @RequestParam(value = "language", required = false) Language language) {
        language = language == null ? Language.EN : language;
        return ResponseEntity.of(genreService.getSingleGenre(id, language));
    }
}
