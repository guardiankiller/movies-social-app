package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.entity.I18nString;
import bg.guardiankiller.moviessocialapp.repository.I18nRepository;
import bg.guardiankiller.moviessocialapp.service.I18nService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class I18nServiceImpl implements I18nService {

    private final I18nRepository repository;

    @Override
    @Transactional
    public UUID addString(Map<Language, String> versions) {
        var placeholder = UUID.randomUUID();
        addString(placeholder, versions);
        return placeholder;
    }

    private void addString(UUID placeholder, Map<Language, String> versions) {
        var entries = versions.entrySet().stream()
                .map(e->new I18nString(e.getKey(), e.getValue()))
                .toList();
        entries.forEach(e -> e.setPlaceholder(placeholder));
        repository.saveAll(entries);
    }

    @Override
    @Transactional
    public void updateString(UUID placeholder, Map<Language, String> versions) {
        var entities = repository.getString(placeholder).stream()
                .collect(Collectors.toMap(I18nString::getLanguage, e->e));
        if(entities.isEmpty()) {
            addString(placeholder, versions);
        }
        for(var entry : versions.entrySet()) {
            var key = entry.getKey();
            if(entities.containsKey(key)) {
                entities.get(key).setValue(entry.getValue());
            } else {
                var e = new I18nString(key, entry.getValue());
                e.setPlaceholder(placeholder);
                repository.save(e);
            }
        }
    }

    @Override
    public Optional<Map<Language, String>> retrieve(UUID placeholder) {
        return Optional.of(repository.getString(placeholder))
                .map(e-> e.stream()
                        .collect(Collectors.toMap(I18nString::getLanguage, I18nString::getValue)))
                .filter(m->!m.isEmpty());
    }

    @Override
    public Optional<String> retrieve(UUID placeholder, Language lang) {
        return repository.getString(placeholder, lang);
    }
}
