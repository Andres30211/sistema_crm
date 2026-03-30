package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	private WebClient webClient;

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
            .bodyToMono(PostFacebookResponseDto.class)
            .flatMap(response -> {
                // Tomamos la lista 'data' del DTO y la guardamos en Mongo
                return Flux.fromIterable(response.getData())
                    .flatMap(this.postfacebookRepository::save) // Guarda cada post reactivamente
                    .then(Mono.just(response)); // Al final devuelve el DTO original
            });
    }
    
    

}
