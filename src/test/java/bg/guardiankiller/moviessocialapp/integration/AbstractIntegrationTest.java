package bg.guardiankiller.moviessocialapp.integration;

import bg.guardiankiller.moviessocialapp.mappings.UserMappings;
import bg.guardiankiller.moviessocialapp.mappings.UserMappingsImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
public abstract class AbstractIntegrationTest {

    private static MySQLContainer<?> MYSQL_SQL_CONTAINER;

    @BeforeAll
    public static void setupDB() {
        MYSQL_SQL_CONTAINER = new MySQLContainer<>("mysql:latest")
                .withDatabaseName("test_db");
        MYSQL_SQL_CONTAINER.start();

    }

    @DynamicPropertySource
    public static void overrideTestProperties(DynamicPropertyRegistry registry) {

        System.out.println(MYSQL_SQL_CONTAINER.getJdbcUrl());
        System.out.println(MYSQL_SQL_CONTAINER.getUsername());
        System.out.println(MYSQL_SQL_CONTAINER.getPassword());

        registry.add("spring.datasource.url", MYSQL_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_SQL_CONTAINER::getPassword);
    }

    @AfterAll
    public static void destroyDB() {
        MYSQL_SQL_CONTAINER.stop();
    }
}
