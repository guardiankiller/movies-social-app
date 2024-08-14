package bg.guardiankiller.moviessocialapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public GenreEntity(long tmdbId, UUID name) {
        this.tmdbId = tmdbId;
        this.name = name;
    }
}
