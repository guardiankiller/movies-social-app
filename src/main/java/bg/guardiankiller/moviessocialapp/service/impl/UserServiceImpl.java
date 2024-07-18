package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
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

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        UserEntity newUserEntity = mapper.map(userRegisterDTO, UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        RoleEntity roleEntity = roleRepository.findByName(UserRoles.USER);
        newUserEntity.addRole(roleEntity);

        userRepository.save(newUserEntity);
    }

}
