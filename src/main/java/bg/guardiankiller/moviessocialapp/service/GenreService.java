package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres(Language lang);
}
