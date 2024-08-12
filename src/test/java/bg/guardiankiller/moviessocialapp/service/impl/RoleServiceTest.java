package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import bg.guardiankiller.moviessocialapp.repository.RoleRepository;
import bg.guardiankiller.moviessocialapp.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    static class MockedRoleRepository {
        private final RoleRepository roleRepository;

        private final Set<RoleEntity> roles = new HashSet<>();

        private int nextId = 0;

        MockedRoleRepository() {
            this.roleRepository = mock(RoleRepository.class);

            when(this.roleRepository.save(any())).thenAnswer((Answer<RoleEntity>) i-> {
                RoleEntity input = i.getArgument(0, RoleEntity.class);
                input.setId(nextId++);
                roles.add(input);
                System.out.println(input.getName());
                System.out.println(input.getId());
                input.setUsers(Set.of());
                System.out.println(input.getUsers());
                return input;
            });
            when(this.roleRepository.findByName(any())).thenAnswer((Answer<RoleEntity>) i-> {
                UserRoles input = i.getArgument(0, UserRoles.class);
                return roles.stream().filter(e->e.getName() == input).findFirst().orElse(null);
            });
        }

        public RoleRepository getMock() {
            return roleRepository;
        }

        public Set<RoleEntity> getRoles() {
            return roles;
        }

        public void addRole(UserRoles role) {
            RoleEntity entity = new RoleEntity();
            entity.setId(nextId++);
            entity.setName(role);
            roles.add(entity);
        }
    }

    @Test
    public void testInit() {
        MockedRoleRepository repo = new MockedRoleRepository();
        RoleService service = new RoleServiceImpl(repo.getMock());
        service.init();
        var actual = repo
                .getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());

        var expected = Arrays.stream(UserRoles.values()).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testInitWhenNotNeeded() {
        MockedRoleRepository repo = new MockedRoleRepository();
        repo.addRole(UserRoles.USER);
        RoleService service = new RoleServiceImpl(repo.getMock());
        service.init();
        var actual = repo
                .getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());

        var expected = Set.of(UserRoles.USER);
        assertEquals(expected, actual);
    }
}