package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import bg.guardiankiller.moviessocialapp.service.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import static bg.guardiankiller.moviessocialapp.config.StaticStorageConfig.IMAGES_STORAGE;
import static bg.guardiankiller.moviessocialapp.config.StaticStorageConfig.BASE_URL;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalStorageServiceImpl implements StorageService {

    private final MoviesImportService importService;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(IMAGES_STORAGE);
    }

    @Override
    public void downloadFromTMDB(Collection<String> paths) {
        try(var stream = Files.list(IMAGES_STORAGE)) {
            var files = stream.map(Path::getFileName).map(Path::toString).toList();
            var set = paths.stream().map(p -> p.replace("/", "")).collect(Collectors.toSet());
            set = new HashSet<>(set);
            files.forEach(set::remove);
            importService.downloadImages(IMAGES_STORAGE, set);
        } catch (IOException e) {
            throw new ServerException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public String retrieveURL(String path) {
        return BASE_URL.replace("{filename}", path.replace("/", ""));
    }
}
