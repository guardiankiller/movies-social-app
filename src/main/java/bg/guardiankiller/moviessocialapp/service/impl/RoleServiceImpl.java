package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.Role;
import bg.guardiankiller.moviessocialapp.repository.RoleRepository;
import bg.guardiankiller.moviessocialapp.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @PostConstruct
    @Transactional
    public void init() {
        if (roleRepository.findByName(UserRoles.USER) != null) {
            return;
        }

        Role defaultRole = new Role();
        defaultRole.setName(UserRoles.USER);
        roleRepository.save(defaultRole);

        Role moderator = new Role();
        moderator.setName(UserRoles.MODERATOR);
        roleRepository.save(moderator);

        Role admin = new Role();
        admin.setName(UserRoles.ADMIN);
        roleRepository.save(admin);
    }
}
