package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ValidationException;
import bg.guardiankiller.moviessocialapp.mappings.UserMappings;
import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.dto.UserChangeDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import bg.guardiankiller.moviessocialapp.model.entity.UserEntity;
import bg.guardiankiller.moviessocialapp.repository.RoleRepository;
import bg.guardiankiller.moviessocialapp.repository.UserRepository;
import bg.guardiankiller.moviessocialapp.service.UserService;
import bg.guardiankiller.moviessocialapp.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final UserMappings mapper;

    @Override
    @Transactional
    public void registerUser(UserRegisterDTO register, boolean dryRun) {
        Set<ConstraintViolation<?>> errors = validationService.validate(register);
        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid registration input", errors);
        }

        if(!dryRun) {
            UserEntity newUserEntity = mapper.map(register, passwordEncoder);
            RoleEntity roleEntity = roleRepository.findByName(UserRoles.USER);
            newUserEntity.addRole(roleEntity);

            userRepository.save(newUserEntity);
        }

    }

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).map(mapper::map);
    }

    @Override
    @Transactional
    public Optional<UserDTO> changeUserInfo(String username, UserChangeDTO change, boolean dryRun) {
        var opt = userRepository.findUserByUsername(username);
        if(opt.isEmpty()) {
            return empty();
        }
        var entity = opt.get();
        Set<ConstraintViolation<?>> errors = validationService.validate(change);
        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid registration input", errors);
        }
        ofNullable(change.getUsername()).ifPresent(entity::setUsername);
        ofNullable(change.getFullName()).ifPresent(entity::setFullName);
        ofNullable(change.getEmail()).ifPresent(entity::setEmail);
        ofNullable(change.getPassword()).map(passwordEncoder::encode).ifPresent(entity::setPassword);

        return of(entity).map(mapper::map);
    }

    @Override
    @Transactional
    public Optional<UserDTO> addUserRoles(String username, UserRoles[] roles) {
        var opt = userRepository.findUserByUsername(username);
        if(opt.isEmpty()) {
            return empty();
        }
        var entity = opt.get();
        for(UserRoles role : roles) {
            entity.addRole(roleRepository.findByName(role));
        }
        return of(entity).map(mapper::map);
    }

    @Override
    @Transactional
    public Optional<UserDTO> removeUserRoles(String username, UserRoles[] roles) {
        var opt = userRepository.findUserByUsername(username);
        if(opt.isEmpty()) {
            return empty();
        }
        var entity = opt.get();
        for(UserRoles role : roles) {
            entity.removeRole(roleRepository.findByName(role));
        }
        return of(entity).map(mapper::map);
    }

    @Override
    @Transactional
    public Optional<UserDTO> setUserRoles(String username, UserRoles[] roles) {
        var opt = userRepository.findUserByUsername(username);
        if(opt.isEmpty()) {
            return empty();
        }
        var entity = opt.get();
        entity.getRoles().clear();
        for(UserRoles role : roles) {
            entity.addRole(roleRepository.findByName(role));
        }
        return of(entity).map(mapper::map);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return userRepository
                .findUserByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}
