package bg.guardiankiller.moviessocialapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
@EnableWebMvc
public class StaticStorageConfig implements WebMvcConfigurer {

    public static final Path IMAGES_STORAGE = Path.of("./images");

    public static final String BASE_URL = "http://localhost:8080/images/{filename}";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(IMAGES_STORAGE.toUri().toString());
    }

}
