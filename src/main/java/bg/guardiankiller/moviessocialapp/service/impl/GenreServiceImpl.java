package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.dto.Genre;
import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;
import bg.guardiankiller.moviessocialapp.repository.GenreRepository;
import bg.guardiankiller.moviessocialapp.service.GenreService;
import bg.guardiankiller.moviessocialapp.service.I18nService;
import bg.guardiankiller.moviessocialapp.service.MoviesImportService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;
    private final MoviesImportService moviesImportService;
    private final I18nService i18nService;

    @PostConstruct
    @Transactional
    public void init() {
        if(repository.count() == 0) {
            var genres = moviesImportService.retrieveAllGenres();
            for(var genre : genres) {
                var placeholder = i18nService.addString(genre.name());
                repository.save(new GenreEntity(genre.id(), placeholder));
            }
        }
    }

    @Override
    public List<Genre> getAllGenres(Language lang) {
        var entities = repository.findAll();
        var nameIds = entities.stream().map(GenreEntity::getName).toList();
        var names = i18nService.retrieve(nameIds, lang);

        return entities.stream()
                .map(e->new Genre(e.getId(), names.get(e.getName())))
                .toList();
    }

    @Override
    public List<GenreEntity> getEntityByTMIDs(Collection<Long> tmIDs) {
        return repository.getByTMDBids(tmIDs);
    }

    @Override
    public Optional<Genre> getSingleGenre(long id, Language lang) {
        return repository.findById(id).map(e->new Genre(e.getId(), retrieve(e.getName(), lang)));
    }

    private String retrieve(UUID placeholder, Language language) {
        return i18nService
                .retrieve(placeholder, language)
                .orElseGet(()->i18nService.retrieve(placeholder, Language.EN).orElse("N/A"));
    }
}
