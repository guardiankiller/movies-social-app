package bg.guardiankiller.moviessocialapp.jwt;

public class JWTExpiredException extends Exception {

    JWTExpiredException(String message) {
        super(message);
    }

    JWTExpiredException(String message, Throwable t) {
        super(message, t);
    }
}
