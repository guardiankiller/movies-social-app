package bg.guardiankiller.moviessocialapp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Movie {

    private long id;
    private double popularity;
    private double voteAverage;
    private long voteCount;
    private LocalDate releaseDate;

    private String imageURL;

    private String title;

    private String overview;

    private List<Genre> genres = new ArrayList<>();
}
