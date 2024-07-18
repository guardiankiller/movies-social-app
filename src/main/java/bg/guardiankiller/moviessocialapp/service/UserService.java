package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);
}
