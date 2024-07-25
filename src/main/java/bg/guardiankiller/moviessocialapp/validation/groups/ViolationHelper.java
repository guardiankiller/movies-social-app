package bg.guardiankiller.moviessocialapp.validation.groups;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

import java.util.*;

public class ViolationHelper {

    private final Map<Path, ConstraintViolation<?>> results = new HashMap<>();

    public <T> void validateObject(Validator validator, T object) {
        for (var group : List.of(Group1.class, Group2.class, Group3.class)) {
            var result = validator.validate(object, group);
            this.addConstraintViolations(result);
        }
    }

    public void addConstraintViolation(ConstraintViolation<?> violation) {
        results.putIfAbsent(violation.getPropertyPath(), violation);
    }

    public void addConstraintViolations(Set<? extends ConstraintViolation<?>> violations) {
        violations.forEach(this::addConstraintViolation);
    }

    public Set<ConstraintViolation<?>> toSet() {
        return new HashSet<>(results.values());
    }

    public boolean hasError() {
        return !results.isEmpty();
    }
}
