package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.ImportMoviesRequest;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "30") int limit) {
        language = language == null ? Language.EN : language;
        return service.getAllMovies(language, PageRequest.of(page-1, limit));
    }
}
