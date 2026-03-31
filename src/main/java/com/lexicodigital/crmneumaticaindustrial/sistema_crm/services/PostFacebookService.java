package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.CommentfacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String accessToken = "EAAcQxNJPHU4BRLc6gTOPw2ZCEcO3bJRPlyiRSZCQwMiWWjzrz7EK95mJveZB9XdnD5Na9Bh8ddgczKZAbmnaYZAz5n1BQQ8Mu0IWGmruKZAZCUgHaSX4U5ZBdLf0KxPJDcJnhardZAhDcrsxEMZATkZBI9lzrTDoSgAcuRtV9d9b6E768ZB313lS3QLTUDOF0iGQ0aQjNCZCkO8C1cskB7tgnUqbrautlMAoTHwzNAwZBaH5zQlwZDZD";
    
	@Transactional
	public ResponseEntity<PostFacebookResponseDto> getSavePostfacebook() {
	    
	    PostFacebookResponseDto response = this.restTemplate.getForObject("https://graph.facebook.com/v25.0/me/posts?fields=id,created_time,story,message,comments&access_token=".concat(accessToken), PostFacebookResponseDto.class);
	
	    if (response != null && response.getData() != null) {
	        List<PostFacebookDto> postsActualizados = response.getData().stream()
	            .map(dto -> {
	                // 1. Consultar si existe por el FB_ID (o ID de Facebook)
	                PostFacebookDto postBBDD = this.postfacebookRepository.findByfbPostId(dto.getFbPostId())
	                                              .orElse(dto); // Si no existe, usamos el DTO nuevo como base

	             // 2. Actualizar campos básicos
	                postBBDD.setMessage(dto.getMessage());
	                postBBDD.setStory(dto.getStory());
	                postBBDD.setFechaCaptura(LocalDateTime.now());

	                // 3. Lógica de Comentarios (IMPORTANTE)
	                // Si el DTO que llegó de la API trae comentarios en su lista interna
	                if (dto.getComentarios() != null) {
	                    
	                    // Limpiamos los anteriores para evitar duplicados en la base de datos
	                    postBBDD.getComentarios().clear();

	                    // Vinculamos cada comentario nuevo con el post actual
	                    dto.getComentarios().forEach(c -> {
	                        c.setPost(postBBDD); // Esto llena la columna post_id en la DB
	                        postBBDD.getComentarios().add(c);
	                    });
	                }

	                return postBBDD;
	            })
	            .collect(Collectors.toList());

	        // 4. Guardar todo el lote procesado
	        this.postfacebookRepository.saveAll(postsActualizados);
	    }

	    return ResponseEntity.ok(response);
	}
	
	@Scheduled(fixedRate = 900000) // 15 minutos en milisegundos
	public void scheduleFacebookSync() {
	    System.out.println("Ejecutando sincronización automática...");
	    this.getSavePostfacebook();
	}

}
