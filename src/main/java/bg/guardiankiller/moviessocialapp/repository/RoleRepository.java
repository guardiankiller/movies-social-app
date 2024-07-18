package bg.guardiankiller.moviessocialapp.repository;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRoles userRole);
}
