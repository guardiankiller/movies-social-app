package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.AuthResponseDTO;

public interface AuthenticationService {

    AuthResponseDTO login(AuthRequestDTO request);

    void loginWithKey(String token);
}
