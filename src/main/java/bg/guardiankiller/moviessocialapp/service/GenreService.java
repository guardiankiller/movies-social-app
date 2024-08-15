package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAllGenres(Language lang);

    List<GenreEntity> getEntityByTMIDs(Collection<Long> tmIDs);

    Optional<Genre> getSingleGenre(long id, Language lang);
}
