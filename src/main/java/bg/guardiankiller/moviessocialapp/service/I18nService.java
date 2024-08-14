package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface I18nService {

    @Transactional
    UUID addString(Map<Language, String> versions);

    @Transactional
    void updateString(UUID placeholder, Map<Language, String> versions);

    Optional<Map<Language, String>> retrieve(UUID placeholder);

    default Optional<String> retrieve(UUID placeholder, Language lang) {
        return retrieve(placeholder).flatMap(map -> Optional.ofNullable(map.get(lang)));
    }

    @Transactional
    default void updateString(UUID placeholder, Language lang, String value) {
        updateString(placeholder, Map.of(lang, value));
    }

    @Transactional
    default void updateString(UUID placeholder, String value) {
        updateString(placeholder, Language.EN, value);
    }

    @Transactional
    default UUID addString(String value) {
        return addString(Language.EN, value);
    }

    @Transactional
    default UUID addString(Language lang, String value) {
        return addString(Map.of(lang, value));
    }

}
