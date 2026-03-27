package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;

import reactor.core.publisher.Mono;

@Service
public class PostFacebookService {
	
	private final WebClient webClient;

    public PostFacebookService(WebClient.Builder webClientBuilder) {
        // Base URL de la API de Graph
        this.webClient = webClientBuilder.baseUrl("https://graph.facebook.com/v25.0").build();
    }

    public Mono<PostFacebookResponseDto> getFacebookUserData(String accessToken) {
        return this.webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/me/posts")
                .queryParam("fields", "id,created_time,story,message")
                .queryParam("access_token", accessToken)
                .build())
            .retrieve()
            .bodyToMono(PostFacebookResponseDto.class);
    }

}
