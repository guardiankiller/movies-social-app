package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.jwt.*;
import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.AuthResponseDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import bg.guardiankiller.moviessocialapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private static final long DURATION_IN_MIN = 30;

    private final JWTEntry keyEntry;

    public AuthenticationServiceImpl(UserService userService, UserDetailsService userDetailsService, JWTEntry keyEntry) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.keyEntry = keyEntry;
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
        claims.addClaim("username", user.username());
        claims.addClaim("fullName", user.fullName());
        var exp = LocalDateTime.now().plusMinutes(DURATION_IN_MIN);
        claims.setExpiration(exp);
        var token = JWTUtils.sign(claims, keyEntry);
        return new AuthResponseDTO(token, exp, "Bearer");
    }

    @Override
    public void loginWithKey(String token) {
        try {
            var claims = JWTUtils.verify(token, keyEntry);
            authenticate(getUsername(claims));
        } catch (JWTInvalidClaimsException | JWTExpiredException | JWTInvalidSignatureException |
                 UsernameNotFoundException e) {
            throw new ServerException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
    }

    private void authenticate(String username) {
        var user = userDetailsService.loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                user.getAuthorities());
        if (SecurityContextHolder
                .getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private String getUsername(JWTClaims claims) {
        return claims.getClaim("username");
    }
}
