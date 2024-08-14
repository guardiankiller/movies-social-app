package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres(Language lang);

    List<GenreEntity> getEntityByTMIDs(Collection<Long> tmIDs);
}
