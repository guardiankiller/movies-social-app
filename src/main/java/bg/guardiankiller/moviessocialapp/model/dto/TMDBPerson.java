package bg.guardiankiller.moviessocialapp.model.dto;

import java.util.Map;

public record TMDBPerson(
        String birthday,
        int gender,
        long id,
        Map<Language, String> name,
        String known_for_department,
        String place_of_birth,
        double popularity,
        String profile_path
) {
}
