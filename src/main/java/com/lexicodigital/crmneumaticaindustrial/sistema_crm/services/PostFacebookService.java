package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
	
	    Optional.ofNullable(response)
        .map(PostFacebookResponseDto::getData)
        .ifPresent(posts -> posts.stream()
            .peek(post -> post.setFechaCaptura(LocalDateTime.now())) // Seteamos la fecha de auditoría
            .forEach(postfacebookRepository::save) // Guardamos cada uno en MySQL
        );

	    return ResponseEntity.ok(response);
	}
	
	@Scheduled(fixedRate = 900000) // 15 minutos en milisegundos
	public void scheduleFacebookSync() {
	    System.out.println("Ejecutando sincronización automática...");
	    this.getSavePostfacebook();
	}

}
