package bg.guardiankiller.moviessocialapp.config;

import bg.guardiankiller.moviessocialapp.filter.AccessTokenFilter;
import bg.guardiankiller.moviessocialapp.jwt.JWTEntry;
import bg.guardiankiller.moviessocialapp.jwt.JWTKeystore;
import bg.guardiankiller.moviessocialapp.repository.UserRepository;
import bg.guardiankiller.moviessocialapp.service.impl.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public JWTEntry authTokenKey() {
        JWTKeystore keystore = JWTKeystore.fromClasspath("token-key-keystore.p12", "changeit");
        return keystore.getEntry("token-key", "changeit");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restFilterChain(AccessTokenFilter filter, HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers( HttpMethod.POST, "/api/users", "/api/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/languages", "/api/settings").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .build();
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(
//                        authorizeRequests ->
//                                authorizeRequests
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        .requestMatchers("/", "/login", "/register", "/actuator/health", "/actuator/info").permitAll()
//                                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .defaultSuccessUrl("/register", true)
//                )
//                .logout(
//                        logout ->
//                                logout
//                                        .logoutUrl("/logout")
//                                        .logoutSuccessUrl("/")
//                                        .invalidateHttpSession(true)
//                )
////                .rememberMe(
////                        remember ->
////                                remember
////                                        .rememberMeParameter("remember")
////                                        .key("remember Me Encryption Key")
////                                        .rememberMeCookieName("rememberMeCookieName")
////                                        .tokenValiditySeconds(10000)
////                )
//                .build();
//    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }
}
