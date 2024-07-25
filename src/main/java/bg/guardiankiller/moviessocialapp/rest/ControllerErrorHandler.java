package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.exception.ValidationException;
import bg.guardiankiller.moviessocialapp.model.dto.ViolationResponseDTO;
import bg.guardiankiller.moviessocialapp.model.dto.ViolationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO: Add javadoc
@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ViolationResponseDTO> handleValidationResponse(ValidationException e) {
        var violations = e.getViolations().stream().map(ViolationDTO::new).toList();
        var result = new ViolationResponseDTO(e.getMessage(), violations);
        return ResponseEntity
                .status(e.getStatus())
                .body(result);
    }
}
