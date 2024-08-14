package bg.guardiankiller.moviessocialapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movies")
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tmdb_id", unique = true)
    private long tmdbId;

    private double popularity;

    @Column(name = "vote_average", nullable = false)
    private double voteAverage;

    @Column(name = "vote_count", nullable = false)
    private long voteCount;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    private UUID title;

    private UUID overview;

    @ManyToMany
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres = new HashSet<>();

    public void addGenre(GenreEntity genre) {
        getGenres().add(genre);
        genre.getMovies().add(this);
    }

/*
        Map<Language, String> poster_path,
        Set<CastPerson> cast,
        Set<CrewPerson> crew,
 */
}
