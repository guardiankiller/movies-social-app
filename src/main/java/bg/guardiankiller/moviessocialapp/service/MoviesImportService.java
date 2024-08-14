package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Pair;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBMovie;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBPerson;

import java.util.List;

public interface MoviesImportService {

    Pair<List<TMDBMovie>, List<TMDBPerson>> importNMovies(int n);
}
