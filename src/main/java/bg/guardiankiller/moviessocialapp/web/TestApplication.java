package bg.guardiankiller.moviessocialapp.web;

import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestApplication implements CommandLineRunner {

    private final MoviesImportService moviesImportService;

    @Override
    public void run(String... args) throws Exception {
        moviesImportService.importNMovies(50);
    }
}
