package bg.guardiankiller.moviessocialapp.jwt;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JWTKeystoreTest {

    @Test
    public void test() {
        JWTKeystore keystore = JWTKeystore.fromClasspath("test-keystore", "changeit");
        assertNotNull(keystore);
        JWTEntry entry = keystore.getEntry("token-key", "changeit");
        assertNotNull(entry);
        assertNotNull(entry.getPrivateKey());
        assertNotNull(entry.getPublicKey());
        assertEquals(List.of("token-key"), keystore.aliases());
    }
}