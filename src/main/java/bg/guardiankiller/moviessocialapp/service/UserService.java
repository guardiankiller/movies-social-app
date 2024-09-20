package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import bg.guardiankiller.moviessocialapp.model.dto.UserChangeDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;

import java.util.Optional;

public interface UserService {
    void registerUser(UserRegisterDTO register, boolean dryRun);

    Optional<UserDTO> findUserByUsername(String username);

    Optional<UserDTO> changeUserInfo(String username, UserChangeDTO change, boolean dryRun);

    Optional<UserDTO> addUserRoles(String username, UserRoles[] roles);

    Optional<UserDTO> removeUserRoles(String username, UserRoles[] roles);

    Optional<UserDTO> setUserRoles(String username, UserRoles[] roles);

    boolean authenticateUser(String username, String password);
}
