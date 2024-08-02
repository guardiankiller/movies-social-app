package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.jwt.JWTEntry;
import bg.guardiankiller.moviessocialapp.jwt.JWTKeystore;
import bg.guardiankiller.moviessocialapp.jwt.JWTUtils;
import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.AuthResponseDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import bg.guardiankiller.moviessocialapp.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private static final long DURATION_IN_MIN = 30;

    private JWTEntry keyEntry;

    @PostConstruct
    public void setup() {
        JWTKeystore keystore = JWTKeystore.fromClasspath("token-key-keystore", "changeit");
        keyEntry = keystore.getEntry("token-key", "changeit");
    }

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        ServerException e = new ServerException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        UserDTO user = userService.findUserByUsername(request.username())
                .orElseThrow(()-> e);
        if (!userService.authenticateUser(request.username(), request.password())) {
            throw e;
        }
        return generateJWT(user);
    }

    private AuthResponseDTO generateJWT(UserDTO user) {
        var claims = JWTUtils.newClaims();
        claims.setSubject("User Details");
        claims.setIssuer("social-app");
        claims.addClaim("username", user.getUsername());
        claims.addClaim("fullName", user.getFullName());
        var exp = LocalDateTime.now().plusMinutes(DURATION_IN_MIN);
        claims.setExpiration(exp);
        var token = JWTUtils.sign(claims, keyEntry);
        return new AuthResponseDTO(token, exp, "Bearer");
    }

    @Override
    public void loginWithKey(String token) {

    }
}
