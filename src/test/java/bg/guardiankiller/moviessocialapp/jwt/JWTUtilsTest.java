package bg.guardiankiller.moviessocialapp.jwt;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilsTest {

    private static final String INVALID_JWT = "test123";
    private static final String INVALID_SIG_JWT = "eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJzb21ldGhpbmciLCJpc3MiOiJUaGlzIiwiZXhwIjoxNzIyODc4NjY3LCJpYXQiOjE3MjI2MTk0Njd9.BAOkj5Wtyq8NpG8bskG3EXABBUg8cFvcfMUOMs-tYOI0Du3FvtkYohT87CaSnSBxWK0gGQdGJrtNcLOc7KBA0j-6mW5jKaCkIZpCG2IRi8cKCTeZEnCaBQQD7Ay0mL-ABp_k1BvD3S1WxWrZ7EbCm-6A_uXkrgYchF2VIa1JgW4AMtxNLMvlEtD0CsmmjL_jZ97kKadYJZrflACaHM6UqnSXWtHMJ44pzCdswweSWGjHvTYQGJnwz6UsRyPrhWEFkkrHjNHoFJC5ZRWa9R0O_9bqQp3RkUO_lx4YKGQ6FzvajLlUqko3YVl7THowHaL2EDCXNT5rJK2wFppV8oTBO0digflmJPDwDedcr_WevWZoUrmr_27XpEIdZ5ldZn7QP0HW6DQc-PkzhwDhDaa8QGbFzv1aanufTkLHLvnOeMg5VFUX8HAoaz1MYc3jfv_M421CtSs5rlWL35B_V7HHdLW0qsIM1YmSHwFl7gDftvy3MtCkmx7BtTTuOvxOVksZ";

    @Test
    public void testVerify() throws JWTInvalidClaimsException, JWTInvalidSignatureException, JWTExpiredException {
        JWTEntry entry = getKey();
        JWTClaims claims = getValidClaims();
        var jwt = JWTUtils.sign(claims, entry);
        var actual = JWTUtils.verify(jwt, entry);
        assertEquals(claims, actual);
    }

    @Test
    public void testVerifyInvalidJWT() {
        JWTEntry entry = getKey();
        JWTInvalidClaimsException e = assertThrows(JWTInvalidClaimsException.class,
                () -> JWTUtils.verify(INVALID_JWT, entry));
        assertEquals("Invalid JWT token", e.getLocalizedMessage());
    }

    @Test
    public void testVerifyInvalidSignature() {
        JWTEntry entry = getKey();
        JWTInvalidSignatureException e = assertThrows(JWTInvalidSignatureException.class,
                () -> JWTUtils.verify(INVALID_SIG_JWT, entry));
        assertEquals("Invalid JWT signature", e.getLocalizedMessage());
    }

    @Test
    public void testVerifyExpired() {
        JWTEntry entry = getKey();
        JWTClaims claims = getExpiredClaims();
        String jwt = JWTUtils.sign(claims, entry);
        JWTExpiredException e = assertThrows(JWTExpiredException.class,
                () -> JWTUtils.verify(jwt, entry));
        assertEquals("The JWT token is expired", e.getLocalizedMessage());
    }

    private static JWTEntry getKey() {
        JWTKeystore keystore = JWTKeystore.fromClasspath("test-keystore", "changeit");
        assertNotNull(keystore);
        JWTEntry entry = keystore.getEntry("token-key", "changeit");
        assertNotNull(entry);
        return entry;
    }

    private static JWTClaims getValidClaims() {
        var issuedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        var exprDate = LocalDateTime.now().plusDays(3).truncatedTo(ChronoUnit.SECONDS);
        var issuer  = "This";
        var subject = "something";
        var claims = JWTUtils.newClaims();
        claims.setExpiration(exprDate);
        claims.setSubject(subject);
        claims.setIssuer(issuer);
        claims.setIssuedAt(issuedAt);
        return claims;
    }

    private static JWTClaims getStaticClaims() {
        var issuedAt = LocalDateTime.parse("2024-08-02T20:24:27");
        var exprDate = LocalDateTime.parse("2024-08-01T20:24:27");
        var issuer  = "This";
        var subject = "something";
        var claims = JWTUtils.newClaims();
        claims.setExpiration(exprDate);
        claims.setSubject(subject);
        claims.setIssuer(issuer);
        claims.setIssuedAt(issuedAt);
        return claims;
    }


    private static JWTClaims getExpiredClaims() {
        var issuedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        var exprDate = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        var issuer  = "This";
        var subject = "something";
        var claims = JWTUtils.newClaims();
        claims.setExpiration(exprDate);
        claims.setSubject(subject);
        claims.setIssuer(issuer);
        claims.setIssuedAt(issuedAt);
        return claims;
    }
}