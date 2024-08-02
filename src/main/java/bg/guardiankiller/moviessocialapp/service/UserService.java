package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;

import java.util.Optional;

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);

    Optional<UserDTO> findUserByUsername(String username);

    boolean authenticateUser(String username, String password);
}
