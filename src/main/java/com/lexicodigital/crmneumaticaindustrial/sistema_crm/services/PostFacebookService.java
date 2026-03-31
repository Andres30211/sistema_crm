package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String accessToken = "EAAcQxNJPHU4BRMOfYtVRQQcZAZAX76LMqakdu3MlFwYsYMIp0qxyjxUQmawafJVFfpKPHJVqncppe5CKBbBG7k4RuyxosZB5KjxdcNHrZCY8xIoEXPhTrQ4eDYbQJ7EY7YT9gyVGWbu3inZBjK0CLTQQRET33ZBA8P5lJ1Qr86K7VAHDIC5u2jDCZCoPgCYb1ZCDgC1ZCSvj3BY9NqrDENiNsD08f6ASI4jWJwSNBHgdLjoMQdHMxAx03fDfVIiE8J4aeeg26VUBhPqnT";
    
	@Transactional
	public ResponseEntity<PostFacebookResponseDto> getSavePostfacebook() {
	    
	    // 1. Construcción correcta de la URL
	    String url = UriComponentsBuilder.fromUriString("https://graph.facebook.com/v25.0/me/posts")
	            .queryParam("fields", "id,created_time,story,message")
	            .queryParam("access_token", accessToken)
	            .toUriString();

	    // 2. Petición síncrona
	    PostFacebookResponseDto response = restTemplate.getForObject(url, PostFacebookResponseDto.class);

	    // 3. Lógica con API Stream
	    if (response != null && response.getData() != null) {
	        System.out.println("Posts recibidos de Facebook: " + response.getData().size()); // Log de control
	        
	        response.getData().forEach(dto -> {
	            PostFacebookDto entity = new PostFacebookDto();
	            entity.setId(dto.getId());
	            entity.setMessage(dto.getMessage());
	            entity.setStory(dto.getStory());
	            entity.setCreated_time(dto.getCreated_time());
	            entity.setFechaCaptura(LocalDateTime.now());

	            System.out.println("Guardando post ID: " + entity.getId()); // Log de control
	            postfacebookRepository.save(entity);
	        });
	    }

	    return ResponseEntity.ok(response);
	}

	// Método auxiliar para asegurar que los datos estén listos para JPA
	private PostFacebookDto mapDtoToEntity(PostFacebookDto dto) {
	    PostFacebookDto entity = new PostFacebookDto();
	    entity.setId(dto.getId());
	    entity.setMessage(dto.getMessage());
	    entity.setStory(dto.getStory());
	    entity.setCreated_time(dto.getCreated_time());
	    return entity;
	}
	
	@Scheduled(fixedRate = 900000)
    public void cronSincronizacionFacebook() {
        System.out.println("Iniciando sincronización automática: " + LocalDateTime.now());
        try {
            this.getSavePostfacebook();
            System.out.println("Sincronización completada con éxito.");
        } catch (Exception e) {
            System.err.println("Error en la tarea programada: " + e.getMessage());
        }
    }

}
