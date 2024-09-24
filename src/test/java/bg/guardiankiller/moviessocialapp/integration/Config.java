package bg.guardiankiller.moviessocialapp.integration;

import bg.guardiankiller.moviessocialapp.mappings.UserMappings;
import bg.guardiankiller.moviessocialapp.mappings.UserMappingsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public UserMappings userMappings() {
        return new UserMappingsImpl();
    }
}
