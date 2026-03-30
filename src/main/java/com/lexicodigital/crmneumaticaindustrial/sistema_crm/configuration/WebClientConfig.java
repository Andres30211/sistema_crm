package com.lexicodigital.crmneumaticaindustrial.sistema_crm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class WebClientConfig {

	@Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
	
	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para que el POST de Facebook entre
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/api/v1/facebook/webhook").permitAll() // Permite el webhook sin login
                .anyExchange().authenticated() // El resto requiere seguridad si la tienes configurada
            )
            .build();
    }
}
