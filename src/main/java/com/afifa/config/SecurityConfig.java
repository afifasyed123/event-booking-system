package com.afifa.config;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import com.afifa.security.JwtAuthenticationFilter;
import com.afifa.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.userDetailsService=customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**", "/auth/**").permitAll()
                        .requestMatchers(
                                        "/users/**",
                                        "/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/events/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/events/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/events/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        "Unauthorized"
                                )
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(
                                        HttpServletResponse.SC_FORBIDDEN,
                                        "Forbidden"
                                )
                        )
                )
        .addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
