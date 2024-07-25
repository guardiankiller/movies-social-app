package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ValidationException;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterRestDTO;
import bg.guardiankiller.moviessocialapp.service.UserRestService;
import bg.guardiankiller.moviessocialapp.service.UserService;
import bg.guardiankiller.moviessocialapp.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRestServiceImpl implements UserRestService {

    private final UserService userService;
    private final ValidationService validationService;
    private final ModelMapper mapper;

    public UserRestServiceImpl(UserService userService, ValidationService validationService, ModelMapper mapper) {
        this.userService = userService;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    @Override
    public void registerUser(UserRegisterRestDTO form, boolean dryRun) {
        Set<ConstraintViolation<?>> errors = validationService.validate(form);
        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid registration input", errors);
        }

        if(!dryRun) {
            UserRegisterDTO dto = mapper.map(form, UserRegisterDTO.class);
            userService.registerUser(dto);
        }
    }
}
