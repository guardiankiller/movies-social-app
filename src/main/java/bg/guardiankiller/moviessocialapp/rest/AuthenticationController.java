package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.AuthResponseDTO;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping
    public AuthResponseDTO requestKey(@RequestBody AuthRequestDTO request) {
        return service.login(request);
    }
}