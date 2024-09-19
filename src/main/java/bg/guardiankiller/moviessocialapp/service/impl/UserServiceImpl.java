package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.mappings.UserMappings;
import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import bg.guardiankiller.moviessocialapp.model.entity.UserEntity;
import bg.guardiankiller.moviessocialapp.repository.RoleRepository;
import bg.guardiankiller.moviessocialapp.repository.UserRepository;
import bg.guardiankiller.moviessocialapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final UserMappings mappings;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper mapper,
        UserMappings mappings) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.mappings = mappings;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        UserEntity newUserEntity = mappings.map(userRegisterDTO, passwordEncoder);
        RoleEntity roleEntity = roleRepository.findByName(UserRoles.USER);
        newUserEntity.addRole(roleEntity);

        userRepository.save(newUserEntity);
    }

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).map(mappings::map);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return userRepository
                .findUserByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}
