package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(UserRoles userRole);
}
