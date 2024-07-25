package bg.guardiankiller.moviessocialapp.service;

import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterRestDTO;

public interface UserRestService {

    void registerUser(UserRegisterRestDTO form, boolean dryRun);
}
