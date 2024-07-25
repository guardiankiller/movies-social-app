package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.service.ValidationService;
import bg.guardiankiller.moviessocialapp.validation.groups.ViolationHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    @Override
    public <T> Set<ConstraintViolation<?>> validate(T object) {
        var helper = new ViolationHelper();
        helper.validateObject(validator, object);
        return helper.toSet();
    }
}
