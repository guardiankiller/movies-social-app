package bg.guardiankiller.moviessocialapp.model.dto;


import java.time.LocalDateTime;

public record AuthResponseDTO(
    String accessToken,
    LocalDateTime expirationTime,
    String type
){}