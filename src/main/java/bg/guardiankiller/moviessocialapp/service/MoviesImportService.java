package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.joinpoint.TrackExecutionTime;
import bg.guardiankiller.moviessocialapp.model.dto.Pair;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBGenre;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBMovie;
import bg.guardiankiller.moviessocialapp.model.dto.TMDBPerson;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface MoviesImportService {

    List<TMDBGenre> retrieveAllGenres();

    List<TMDBPerson> retrievePeopleFromMovies(Collection<TMDBMovie> movies);

    List<TMDBPerson> retrievePeopleByIds(Collection<Long> ids);

    TMDBPerson retrievePersonById(long id);

    List<TMDBMovie> retrieveNMoviesFromEachGenre(int n);

    List<TMDBMovie> retrieveMovies(Collection<Long> ids);

    TMDBMovie retrieveMovie(long id);

    Pair<List<TMDBMovie>, List<TMDBPerson>> importNMovies(int n);

    @TrackExecutionTime
    default List<Path> downloadImages(Path temp, String... paths) {
        return downloadImages(temp, Arrays.asList(paths));
    }

    List<Path> downloadImages(Path temp, Collection<String> paths);
}
