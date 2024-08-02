package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterRestDTO;

import java.util.Optional;

public interface UserRestService {

    void registerUser(UserRegisterRestDTO form, boolean dryRun);

    Optional<UserDTO> findUserByUsername(String username);
}
