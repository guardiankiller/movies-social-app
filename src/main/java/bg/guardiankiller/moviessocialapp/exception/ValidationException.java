package bg.guardiankiller.moviessocialapp.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Set;

// TODO: Add javadoc
@Getter
public class ValidationException extends ServerException {

    private final Collection<? extends ConstraintViolation<?>> violations;

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        super(HttpStatus.BAD_REQUEST);
        this.violations = violations;
    }

    public ValidationException(String message, Set<? extends ConstraintViolation<?>> violations) {
        super(message, HttpStatus.BAD_REQUEST);
        this.violations = violations;
    }

    public ValidationException(HttpStatus status, String message, Set<? extends ConstraintViolation<?>> violations) {
        super(message, status);
        this.violations = violations;
    }
}
