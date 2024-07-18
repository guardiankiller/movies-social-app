package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username);
}
