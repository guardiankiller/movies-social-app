package bg.guardiankiller.moviessocialapp.filter;

import bg.guardiankiller.moviessocialapp.exception.ServerException;
import bg.guardiankiller.moviessocialapp.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenFilterTest {

    @ParameterizedTest
    @CsvSource({"None", "blank", "test123"})
    public void passWhenNoAuthorizationHeader(String headerValue) throws ServletException, IOException {
        if("blank".equals(headerValue)) headerValue = "";
        HttpServletRequest request = mockedRequest("None".equals(headerValue) ? Map.of() : Map.of(AccessTokenFilter.HEADER_AUTHORIZATION, headerValue));
        FilterChainMock filterChainMock = new FilterChainMock();
        ResponseMock responseMock = new ResponseMock();
        AuthenticationServiceMock authenticationServiceMock = new AuthenticationServiceMock();

        AccessTokenFilter filter = new AccessTokenFilter(authenticationServiceMock.mock);
        filter.doFilterInternal(request, responseMock.mock, filterChainMock.mock);
        assertTrue(filterChainMock.calledCount >= 1);
        assertEquals(0, authenticationServiceMock.calledCount);
        assertFalse(responseMock.hasStatus());
    }

    @Test
    public void passWhenAuthorizationHeaderValid() throws ServletException, IOException {
        HttpServletRequest request = mockedRequest(Map.of(AccessTokenFilter.HEADER_AUTHORIZATION, AccessTokenFilter.BEARER_TYPE + " test123"));
        FilterChainMock filterChainMock = new FilterChainMock();
        ResponseMock responseMock = new ResponseMock();
        AuthenticationServiceMock authenticationServiceMock = new AuthenticationServiceMock();

        AccessTokenFilter filter = new AccessTokenFilter(authenticationServiceMock.mock);
        filter.doFilterInternal(request, responseMock.mock, filterChainMock.mock);
        assertTrue(filterChainMock.calledCount >= 1);
        assertEquals(1, authenticationServiceMock.calledCount);
        assertFalse(responseMock.hasStatus());
    }

    @Test
    public void failWhenAuthorizationFailed() throws ServletException, IOException {
        HttpServletRequest request = mockedRequest(Map.of(AccessTokenFilter.HEADER_AUTHORIZATION, AccessTokenFilter.BEARER_TYPE + " test123"));
        FilterChainMock filterChainMock = new FilterChainMock();
        ResponseMock responseMock = new ResponseMock();
        AuthenticationServiceMock authenticationServiceMock = new AuthenticationServiceMock(true);

        AccessTokenFilter filter = new AccessTokenFilter(authenticationServiceMock.mock);
        filter.doFilterInternal(request, responseMock.mock, filterChainMock.mock);
        assertEquals(0, filterChainMock.calledCount);
        assertEquals(1, authenticationServiceMock.calledCount);
        assertTrue(responseMock.hasStatus());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseMock.getStatus());
    }

    private static HttpServletRequest mockedRequest(Map<String, String> headers) {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getHeader(Mockito.anyString()))
                .thenAnswer((Answer<String>) i-> headers.get(i.getArgument(0, String.class)));
        return req;
    }

    static class FilterChainMock {
        private int calledCount = 0;

        private final FilterChain mock;

        FilterChainMock() throws ServletException, IOException {
            this.mock = Mockito.mock(FilterChain.class);
            Mockito.doAnswer((Answer<?>) i-> {
                calledCount++;
                return null;
            }).when(mock).doFilter(Mockito.any(), Mockito.any());
        }
    }

    static class AuthenticationServiceMock {
        private int calledCount = 0;

        private final AuthenticationService mock;

        AuthenticationServiceMock() throws ServletException, IOException { this(false); }

        AuthenticationServiceMock(boolean throwExc) throws ServletException, IOException {
            this.mock = Mockito.mock(AuthenticationService.class);
            Mockito.doAnswer((Answer<?>) i-> {
                calledCount++;
                if(throwExc) {
                    throw new ServerException("Test123", HttpStatus.BAD_REQUEST);
                }
                return null;
            }).when(mock).loginWithKey(Mockito.anyString());
        }
    }

    static class ResponseMock {
        private int status = -1;

        private final HttpServletResponse mock;

        ResponseMock() {
            this.mock = Mockito.mock(HttpServletResponse.class);
            Mockito.doAnswer((Answer<?>) i-> {
                status = i.getArgument(0, Integer.class);
                return null;
            }).when(mock).setStatus(Mockito.anyInt());
        }

        public boolean hasStatus() {
            return status != -1;
        }

        public int getStatus() {
            return status;
        }
    }
}