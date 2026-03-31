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

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String accessToken = "EAAcQxNJPHU4BRK85UgSHfENHUArP5m3yhUAkmuwkf0IGk5IULpXozEWBaBONOYQ7MhHqZAdXoU1yAjdcqQpfMqJUcgpd7sqCZBalnAdo5PJoladxcJrJzvZA3Ub8nx9S7IgqNWi1YILhBfurD18ItJ7u6D5QBMEksqGYNeX0ZBvS0REq6XKnpk6SoLuGvg4pT7BeN6ky5F2BU9kRZAA8s0pjAZAgJMzEkkzRIMFwZDZD";
    
	@Transactional
	public ResponseEntity<PostFacebookResponseDto> getSavePostfacebook() {
	    
	    PostFacebookResponseDto response = this.restTemplate.getForObject("https://graph.facebook.com/v25.0/me/posts?fields=id,created_time,story,message,comments&access_token=".concat(accessToken), PostFacebookResponseDto.class);
	
	    if (response != null && response.getData() != null) {
	        List<PostFacebookDto> postsActualizados = response.getData().stream()
	            .map(dto -> {
	                // 1. Consultar si existe por el FB_ID (o ID de Facebook)
	                PostFacebookDto postBBDD = this.postfacebookRepository.findByfbPostId(dto.getFbPostId())
	                                              .orElse(dto); // Si no existe, usamos el DTO nuevo como base

	                // 2. Actualizar datos básicos (siempre viene bien refrescar message o story)
	                postBBDD.setMessage(dto.getMessage());
	                postBBDD.setStory(dto.getStory());
	                postBBDD.setFechaCaptura(LocalDateTime.now());

	                // 3. Extraer y actualizar conteos (Likes y Comments)
	                // Esto sobreescribe los números viejos con los actuales de la API
	                //postBBDD.setLikesCount(dto.getLikes().getSummary().getTotalCount());
	                //postBBDD.setCommentsCount(dto.getComments().getSummary().getTotalCount());

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
