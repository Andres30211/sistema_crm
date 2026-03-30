package com.lexicodigital.crmneumaticaindustrial.sistema_crm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class WebClientConfig {

	@Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Vital para que el POST de Facebook entre
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/facebook/webhook").permitAll() // Permite el webhook
                .anyRequest().permitAll() // Por ahora permite todo para probar
            );
        
        return http.build();
    }
}
