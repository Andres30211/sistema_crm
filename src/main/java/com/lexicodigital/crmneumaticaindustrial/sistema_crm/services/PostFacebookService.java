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
	
	private String accessToken = "EAAcQxNJPHU4BROUdizNb8DXHk6CsXTihR7oEUEGd60OzXvZBbBaYZC3iTZCerkpSY51MPMzUZBlN3K8khUzkWkZCLQu1RQMdXvkacSEtgLr58ZAZBOn7S9WPq0aHdIkdOhzvWr5v6HPJKR2MkEiNrJnIYIVPvlF0bvaHo7kM8SbRDuYA1PCuoYPVVXYMx5pZBQvtA2Fxrf8bCeKimla6961VUx1zZCVW289ZC4LBt7WFavrqUGS71DgzIuuUZBZBqwqs31n3yVWvoldOKYoZD";

    public PostFacebookService(WebClient.Builder webClientBuilder) {
        // Base URL de la API de Graph
        this.webClient = webClientBuilder.baseUrl("https://graph.facebook.com/v25.0").build();
    }

    public Mono<PostFacebookResponseDto> getFacebookUserData() {
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
