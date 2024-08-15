package bg.guardiankiller.moviessocialapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfiguration {

    @Value("${tmdb.api.key}")
    private String ACCESS_TOKEN = "";

    @Bean
    @Primary
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public WebClient tmdbWebClient() {
        final int size = 32 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .defaultHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public WebClient tmdbImagesWebClient() {
        final int size = 64 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl("https://image.tmdb.org/t/p/w500/")
                .defaultHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .exchangeStrategies(strategies)
                .build();
    }
}
