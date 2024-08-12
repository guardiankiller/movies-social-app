package bg.guardiankiller.moviessocialapp.service.impl;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.jwt.*;
import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import bg.guardiankiller.moviessocialapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Test
    public void testVerifyAccessToken() throws JWTInvalidClaimsException, JWTInvalidSignatureException, JWTExpiredException {
        AuthRequestDTO req = new AuthRequestDTO("test", "test");
        UserDTO user = new UserDTO();
        user.setUsername("nikiavatar98");
        user.setFullName("nikiavatar98@abv.bg");
        UserService userService = mockUserService(user, true);
        JWTEntry key = getKey();
        UserDetailsService userDetailsService = mockUserDetailsService("test", "test");
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService, userDetailsService, key);
        var token = authenticationService.login(req);
        JWTClaims claims = JWTUtils.verify(token.accessToken(), key);
        assertEquals(user.getUsername(), claims.getClaim("username"));
        assertEquals(user.getFullName(), claims.getClaim("fullName"));

        AtomicReference<UsernamePasswordAuthenticationToken> auth = new AtomicReference<>();
        SecurityContext securityContext = mock(SecurityContext.class);
        doAnswer((Answer<Void>) i-> {
            auth.set(i.getArgument(0, UsernamePasswordAuthenticationToken.class));
            return null;
        }).when(securityContext).setAuthentication(any());
        when(securityContext.getAuthentication()).thenAnswer((Answer<Authentication>) i-> auth.get() );
        SecurityContextHolder.setContext(securityContext);
        authenticationService.loginWithKey(token.accessToken());
        assertEquals("test", auth.get().getName());
        assertEquals("test", auth.get().getCredentials());
    }

    @Test
    public void testLogin() throws JWTInvalidClaimsException, JWTInvalidSignatureException, JWTExpiredException {
        AuthRequestDTO req = new AuthRequestDTO("test", "test");
        UserDTO user = new UserDTO();
        user.setUsername("nikiavatar98");
        user.setFullName("nikiavatar98@abv.bg");
        UserService userService = mockUserService(user, true);
        JWTEntry key = getKey();
        UserDetailsService userDetailsService = null;
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService, userDetailsService, key);
        var token = authenticationService.login(req);
        JWTClaims claims = JWTUtils.verify(token.accessToken(), key);
        assertEquals(user.getUsername(), claims.getClaim("username"));
        assertEquals(user.getFullName(), claims.getClaim("fullName"));
    }

    @Test
    public void testLoginWhenUserNotFound() {
        AuthRequestDTO req = new AuthRequestDTO("test", "test");
        UserService userService = mockUserService(null, true);
        JWTEntry key = getKey();
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService, null, key);
        ServerException e = assertThrows(ServerException.class, ()-> authenticationService.login(req));
        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
    }

    @Test
    public void testLoginWhenPasswordNotValid() {
        AuthRequestDTO req = new AuthRequestDTO("test", "test");
        UserService userService = mockUserService(new UserDTO(), false);
        JWTEntry key = getKey();
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userService, null, key);
        ServerException e = assertThrows(ServerException.class, ()-> authenticationService.login(req));
        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
    }

    private static UserDetailsService mockUserDetailsService(String username, String password) {
        UserDetailsService service = mock(UserDetailsService.class);
        when(service.loadUserByUsername(anyString())).thenReturn(new User(username, password, List.of()));
        return service;
    }

    private static UserService mockUserService(UserDTO dummyUser, boolean validPassword) {
        UserService userService = mock(UserService.class);
        when(userService.findUserByUsername(anyString()))
                .thenReturn(Optional.ofNullable(dummyUser));
        when(userService.authenticateUser(anyString(), anyString())).thenReturn(validPassword);
        return userService;
    }
    private static JWTEntry getKey() {
        JWTKeystore keystore = JWTKeystore.fromClasspath("test-keystore", "changeit");
        assertNotNull(keystore);
        JWTEntry entry = keystore.getEntry("token-key", "changeit");
        assertNotNull(entry);
        return entry;
    }

}