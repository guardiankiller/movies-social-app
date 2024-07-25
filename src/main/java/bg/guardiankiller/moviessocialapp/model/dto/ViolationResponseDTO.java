package bg.guardiankiller.moviessocialapp.model.dto;

import java.util.Collection;

// TODO: Add javadoc
public record ViolationResponseDTO(
        String message,
        Collection<ViolationDTO> errors
) { }
