package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
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

        RoleEntity defaultRoleEntity = new RoleEntity();
        defaultRoleEntity.setName(UserRoles.USER);
        roleRepository.save(defaultRoleEntity);

        RoleEntity moderator = new RoleEntity();
        moderator.setName(UserRoles.MODERATOR);
        roleRepository.save(moderator);

        RoleEntity admin = new RoleEntity();
        admin.setName(UserRoles.ADMIN);
        roleRepository.save(admin);
    }
}
