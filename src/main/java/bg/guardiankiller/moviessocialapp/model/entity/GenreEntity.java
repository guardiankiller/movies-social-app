package bg.guardiankiller.moviessocialapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "genres")
@NoArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID name;

    @Column(name = "tmdb_id", unique = true)
    private long tmdbId;

    @ManyToMany(mappedBy = "genres")
    private Set<MovieEntity> movies = new HashSet<>();

    public GenreEntity(long tmdbId, UUID name) {
        this.tmdbId = tmdbId;
        this.name = name;
    }

    public void addMovie(MovieEntity movie) {
        this.getMovies().add(movie);
        movie.getGenres().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
