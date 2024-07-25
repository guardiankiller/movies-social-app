package bg.guardiankiller.moviessocialapp.model.dto;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

// TODO: Add javadoc
public record ViolationDTO(String property, String message, String type) {
    public ViolationDTO(ConstraintViolation<?> violation) {
        this(
                violation.getPropertyPath().toString(),
                violation.getPropertyPath().toString().isBlank() ? "object" : "property",
                violation.getMessage());
    }
}
