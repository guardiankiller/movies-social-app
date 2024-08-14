package bg.guardiankiller.moviessocialapp.web;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestApplication implements CommandLineRunner {

    private final MoviesImportService moviesImportService;

    @Override
    public void run(String... args) throws Exception {
        var response = moviesImportService.importNMovies(1);
        var temp = Path.of(System.getProperty("java.io.tmpdir"), "tempMS"+System.currentTimeMillis());
        Files.createDirectories(temp);
        System.out.println(response);
        var paths = response.first().stream().map(x->x.poster_path().get(Language.EN)).collect(Collectors.toSet());
        System.out.println(moviesImportService.downloadImages(temp, paths));
        Files.deleteIfExists(temp);
    }
}
