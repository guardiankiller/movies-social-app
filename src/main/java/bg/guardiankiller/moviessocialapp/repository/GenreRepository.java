package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    @Query("SELECT g FROM GenreEntity g WHERE g.tmdbId = :id")
    Optional<GenreEntity> getByTMDBid(@Param("id") long id);

    @Query("SELECT g FROM GenreEntity g WHERE g.tmdbId IN :ids")
    List<GenreEntity> getByTMDBids(@Param("ids") Collection<Long> ids);
}
