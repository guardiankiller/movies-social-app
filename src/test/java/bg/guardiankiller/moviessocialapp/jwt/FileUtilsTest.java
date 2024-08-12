package bg.guardiankiller.moviessocialapp.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test123,ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae",
            "bazZaz,90b830f90029f562f22aa4b26a5eb1846f1c1b0c6cb587d5273a013e9ff5755d",
            "gasZaz,de5cefbd26feef36e441ff6efaae1aa2c11009f8b331bf7f47bb0590408f3040"
    })
    void calculateSha256(String text, String expected, @TempDir Path tempDir) throws IOException {
        Path temp = tempDir.resolve("file");
        Files.writeString(temp, text, StandardOpenOption.CREATE);
        String actual = FileUtils.calculateSha256(temp);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}