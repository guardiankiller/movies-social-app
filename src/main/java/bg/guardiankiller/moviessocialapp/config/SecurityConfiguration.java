package bg.guardiankiller.moviessocialapp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/", "/login", "/register").permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                // Where is our custom login form?
                                .loginPage("/login")
                                // What is the name of the username parameter in the login Post request?
                                .usernameParameter("email")
                                // What is the name of the password parameter in the login Post request?
                                .passwordParameter("password")
                                // What will happen if the login is successful?
                                .defaultSuccessUrl("/", true)
                                // What will happen if the login fails?
//                                .failureForwardUrl("users/login-error")
                )
                .logout(
                        logout ->
                                logout
                                        // What is the logout url?
                                        .logoutUrl("/users/logout")
                                        // Where to go after successful logout?
                                        .logoutSuccessUrl("/")
                                        // Invalidate the session after logout
                                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }
}