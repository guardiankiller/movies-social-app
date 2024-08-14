package bg.guardiankiller.moviessocialapp.model.dto;

import java.util.Map;

public record TMDBGenre(
        long id,
        Map<Language, String> name
) {
}
