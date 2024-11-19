package com.apiecommerce.apiecomerce.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.apiecommerce.apiecomerce.server.services.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Value("${api.security.route.prod}")
    private String secretProd;
    @Value("${api.security.route.dev}")
    private String secretDev;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOriginPattern(secretProd);
                    corsConfiguration.addAllowedOriginPattern(secretDev);
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.setAllowCredentials(true);

                    return corsConfiguration;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/static/**").permitAll() // Permite acesso a arquivos estáticos
                        .requestMatchers("/css/**").permitAll() // Permite CSS
                        .requestMatchers("/js/**").permitAll() // Permite JavaScript

                        .requestMatchers("/api/admin").hasRole("ADMIN")

                        .requestMatchers("/api/produtos").permitAll()
                        .requestMatchers("/api/produtos/adicionar").hasRole("ADMIN")

                        .requestMatchers("/api/categoria").permitAll()
                        .requestMatchers("/api/categoria/adicionar").hasRole("ADMIN")

                        .requestMatchers("/api/sacola/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/mercadopago").permitAll()

                        .requestMatchers("swagger-ui/**", "/v3/api-docs/**", "/doc").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}