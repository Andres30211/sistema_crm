package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
	
	private String accessToken = "EAAcQxNJPHU4BRDZCSA7DCHWBvmtBPUZCdSzPt1wiTgVi8byS5nQf5kxfmNeXhIXZB4MZAhVF5QP7CBdGhOC51JKoOaC7z53f5kC1BEKLSNS0khxjiygGfLaQ6JsmdkbroTMQZAgxjPr052GT3OyLDuq0qG8Vy3TVzoCe8VppnGiZCW3DRpfl0URvP3CptS7T0argjEYQ7VRYt6zHr0g93Cn3oXBdDqtxofQiTGd6AZD";

    public PostFacebookService(WebClient.Builder webClientBuilder) {
        // Base URL de la API de Graph
        this.webClient = webClientBuilder.baseUrl("https://graph.facebook.com/v25.0").build();
    }

    public Mono<PostFacebookResponseDto> getSaveFacebookPosts() {
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
    
    @Scheduled(fixedRate = 1800000) 
    public void scheduledSync() {
        getSaveFacebookPosts()
            .doOnSuccess(res -> System.out.println("Sincronización automática completada"))
            .doOnError(error -> System.out.println("Error en la Sincronización"))
            .subscribe(); // Necesario para que empiece el flujo
    }

}
