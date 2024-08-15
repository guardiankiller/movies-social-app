package bg.guardiankiller.moviessocialapp.model.dto;

import java.util.Map;
import java.util.Set;

public record TMDBMovie(
        long id,
        Set<Long> genres,
        Map<Language, String> overview,
        Map<Language, String> title,
        String release_date,
        Map<Language, String> poster_path,
        Set<CastPerson> cast,
        Set<CrewPerson> crew,
        double popularity,
        double vote_average,
        long vote_count
) {
}
