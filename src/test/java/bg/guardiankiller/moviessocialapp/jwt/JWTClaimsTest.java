package bg.guardiankiller.moviessocialapp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JWTClaimsTest {

    @Test
    public void testAdditionalClaims() {
        JWTClaims claims = new JWTClaims();
        claims.addClaim("test", "best");
        claims.addClaim("zest", "kest");
        assertEquals(claims.getClaim("test"), "best");
        assertEquals(claims.getClaim("zest"), "kest");
        assertNull(claims.getClaim("no"));
    }

    @Test
    public void testCreateJWTClaimsFromClaims() {
        var issuedAt = LocalDateTime.parse("2024-08-02T20:24:27");
        var exprDate = LocalDateTime.parse("2024-08-05T20:24:27");
        var issuer  = "This";
        var subject = "something";
        var additionalClaimKey = "zest";
        var additionalClaimValue = "best";
        var claims = Jwts.claims(new HashMap<>(Map.of(
                        additionalClaimKey, additionalClaimValue)))
                .setExpiration(toDate(exprDate))
                .setIssuer(issuer)
                .setIssuedAt(toDate(issuedAt))
                .setSubject(subject);
        var jwtClaims = new JWTClaims(claims);
        assertEquals(subject, jwtClaims.getSubject());
        assertEquals(issuer, jwtClaims.getIssuer());
        assertEquals(issuedAt, jwtClaims.getIssuedAt());
        assertEquals(exprDate, jwtClaims.getExpiration());
        assertEquals(additionalClaimValue, jwtClaims.getClaim(additionalClaimKey));
        assertEquals(Map.of(additionalClaimKey, additionalClaimValue), jwtClaims.getAdditionalClaims());
        assertEquals(claims, jwtClaims.toClaims());
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}