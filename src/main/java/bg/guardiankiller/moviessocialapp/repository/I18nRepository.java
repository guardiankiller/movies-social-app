package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import bg.guardiankiller.moviessocialapp.model.entity.I18nString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface I18nRepository extends JpaRepository<I18nString, Long> {

    @Query("SELECT value FROM I18nString WHERE placeholder = :placeholder AND language = :language")
    Optional<String> getString(@Param("placeholder") UUID placeholder, @Param("language") Language language);

    @Query("SELECT i FROM I18nString i WHERE i.placeholder = :placeholder")
    List<I18nString> getString(@Param("placeholder") UUID placeholder);
}
