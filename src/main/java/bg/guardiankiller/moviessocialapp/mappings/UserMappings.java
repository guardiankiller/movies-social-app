package bg.guardiankiller.moviessocialapp.mappings;

import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.model.entity.RoleEntity;
import bg.guardiankiller.moviessocialapp.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Mapper(
    imports = { Collectors.class, RoleEntity.class },
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMappings {

    @Mapping(target = "password",
        expression = "java(encoder.encode(register.getPassword()))")
    UserEntity map(UserRegisterDTO register, PasswordEncoder encoder);

    @Mapping(target = "roles",
        expression = "java(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))")
    UserDTO map(UserEntity entity);
}
