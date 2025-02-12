package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("SELECT m FROM MovieEntity m")
    Page<MovieEntity> getAllMovies(Pageable pageable);

    @Query("SELECT m FROM MovieEntity m LEFT JOIN m.genres g WHERE g.id = :id")
    Page<MovieEntity> getMoviesByGenreId(@Param("id") long genreId, Pageable pageable);
}
