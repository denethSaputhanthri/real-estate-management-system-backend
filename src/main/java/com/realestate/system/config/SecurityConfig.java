package com.realestate.system.config;

import com.realestate.system.security.CustomUserDetailsService;
import com.realestate.system.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/auth/**"
                        ).permitAll()

                        // Properties
                        // Property - anyone authenticated can view
                        .requestMatchers(HttpMethod.GET, "/api/properties/**")
                        .hasAnyRole("CUSTOMER", "SELLER", "AGENT", "ADMIN")

                        // Property - only seller, agent and admin can modify
                        .requestMatchers(HttpMethod.POST, "/api/properties/**")
                        .hasAnyRole("SELLER", "AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/properties/**")
                        .hasAnyRole("SELLER", "AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/properties/**")
                        .hasAnyRole("SELLER", "AGENT", "ADMIN")


                        // Inquiries
                        .requestMatchers(HttpMethod.GET, "/api/inquiries/**")
                        .hasAnyRole("CUSTOMER", "SELLER", "AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/inquiries/**")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/inquiries/**")
                        .hasAnyRole("AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/inquiries/**")
                        .hasRole("ADMIN")

                        // Bookings
                        .requestMatchers(HttpMethod.POST, "/api/bookings")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/bookings")
                        .hasAnyRole("AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/bookings/**")
                        .hasAnyRole("CUSTOMER", "AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/bookings/**")
                        .hasAnyRole("AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/bookings/**")
                        .hasAnyRole("AGENT", "ADMIN")

                        // Transactions
                        .requestMatchers(HttpMethod.POST, "/api/transactions")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/transactions")
                        .hasAnyRole("AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/transactions/**")
                        .hasAnyRole("CUSTOMER", "AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/transactions/**")
                        .hasAnyRole("AGENT", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/transactions/**")
                        .hasAnyRole("AGENT", "ADMIN")

                        // User management - ADMIN only
                        .requestMatchers("/api/user/**")
                        .hasRole("ADMIN")

                        // Everything else still requires authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
