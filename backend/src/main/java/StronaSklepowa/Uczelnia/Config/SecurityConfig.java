package StronaSklepowa.Uczelnia.Config;

import StronaSklepowa.Uczelnia.Services.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/webhooks/**", "/api/products/**", "/api/categories/**", "/login/**", "/oauth2/**").permitAll() 
                .anyRequest().authenticated() 
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService) 
                )
                .defaultSuccessUrl("http://localhost:5173/dashboard", true) 
            ).formLogin(form -> form
                .loginPage("/login") 
                .loginProcessingUrl("/api/login") 
                .defaultSuccessUrl("http://localhost:5173/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("http://localhost:5173/")
                .permitAll()
            );

        return http.build();
    }
}