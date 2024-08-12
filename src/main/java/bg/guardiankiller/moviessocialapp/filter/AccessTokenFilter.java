package bg.guardiankiller.moviessocialapp.filter;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_TYPE = "Bearer ";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    private final AuthenticationService service;

    public AccessTokenFilter(AuthenticationService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var header = request.getHeader(HEADER_AUTHORIZATION);
        if(header != null && !header.isBlank() && header.startsWith(BEARER_TYPE)) {
            var token = header.replace(BEARER_TYPE,"");
            try {
                service.loginWithKey(token);
            } catch (ServerException e) {
                log.error("Failed to login: {}", e.getLocalizedMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}