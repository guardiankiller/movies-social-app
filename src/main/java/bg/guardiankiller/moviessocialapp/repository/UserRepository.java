package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
