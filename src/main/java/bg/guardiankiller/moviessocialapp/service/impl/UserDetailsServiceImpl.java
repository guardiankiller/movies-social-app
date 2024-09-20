package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import bg.guardiankiller.moviessocialapp.model.entity.UserEntity;
import bg.guardiankiller.moviessocialapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository
                .findUserByUsername(username)
                .map(UserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    //TODO: User mapstruct
    private static UserDetails map(UserEntity userEntity) {
        return org.springframework.security.core.userdetails.User
                   .withUsername(userEntity.getUsername())
                   .password(userEntity.getPassword())
                   .authorities(userEntity.getRoles().stream()
                                          .map(RoleEntity::getName)
                                    .map(UserRoles::name)
                                    .map(SimpleGrantedAuthority::new)
                                    .toList())
                   .disabled(false)
                   .build();
    }
}
