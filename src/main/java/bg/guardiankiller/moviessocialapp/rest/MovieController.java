package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.ImportMoviesRequest;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;

    @PostMapping
    public void importMovies(@RequestBody ImportMoviesRequest request) {
        service.importMovies(request.numberOfMovies());
    }

    @GetMapping
    public Page<Movie> getAllMovies(
            @RequestParam(value = "language", required = false) Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "30") int limit) {
        language = language == null ? Language.EN : language;
        return service.getAllMovies(language, PageRequest.of(page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getSingleMovie(
            @PathVariable long id,
            @RequestParam(value = "language", required = false) Language language) {
        language = language == null ? Language.EN : language;
        return ResponseEntity.of(service.getSingleMovie(id, language));
    }
}
