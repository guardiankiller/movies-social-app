package bg.guardiankiller.moviessocialapp.model.dto;

import bg.guardiankiller.moviessocialapp.model.UserRoles;

import java.util.HashSet;
import java.util.Set;

public record UserDTO(
    long id,
    String username,
    String password,
    String fullName,
    String email,
    Set<UserRoles> roles
) {

}
