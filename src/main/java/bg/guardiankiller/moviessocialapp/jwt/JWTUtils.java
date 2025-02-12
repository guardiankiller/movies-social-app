package bg.guardiankiller.moviessocialapp.jwt;

import io.jsonwebtoken.*;

public class JWTUtils {

    public static String sign(JWTClaims claims, JWTEntry key) {
        return Jwts.builder()
                   .setClaims(claims.toClaims())
                   .signWith(SignatureAlgorithm.RS512, key.getPrivateKey())
                   .compact();
    }

    public static JWTClaims verify(String token, JWTEntry key)
        throws JWTInvalidClaimsException, JWTExpiredException, JWTInvalidSignatureException {
        try {
            return new JWTClaims(Jwts.parser()
                                     .setSigningKey(key.getPublicKey())
                                     .parseClaimsJws(token)
                                     .getBody());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new JWTInvalidClaimsException("Invalid JWT token", e);
        } catch (SignatureException e) {
            throw new JWTInvalidSignatureException("Invalid JWT signature", e);
        } catch (ExpiredJwtException e) {
            throw new JWTExpiredException("The JWT token is expired", e);
        }
    }

    public static JWTClaims newClaims() {
        return new JWTClaims();
    }
}
