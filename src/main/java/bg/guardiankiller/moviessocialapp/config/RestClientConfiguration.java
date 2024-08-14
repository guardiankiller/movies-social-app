package bg.guardiankiller.moviessocialapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfiguration {

    private static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NGNlMjM1YTE3NTQwMGM5ZDQzMWIwY2MyYWQzYTgxZiIsIm5iZiI6MTcyMzQ4NDk2MC45Mzc3NDcsInN1YiI6IjY2OWQxNmU4Mzk0ZDFjOTUyYjhlY2I5YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.G_xbiBhgiwxw4PbhUk3jHGypo8v72iAueblRJbvpPvI";

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
}
