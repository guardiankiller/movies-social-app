package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Pair;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBMovie;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBPerson;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface MoviesImportService {

    Pair<List<TMDBMovie>, List<TMDBPerson>> importNMovies(int n);

    List<Path> downloadImages(Path temp, String... paths);

    List<Path> downloadImages(Path temp, Collection<String> paths);
}
