package bg.guardiankiller.moviessocialapp.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {
    EN("en-US"), BG("bg");

    private final String code;
}