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
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.PostFacebookEntity;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.mappers.FacebookMapper;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FacebookMapper facebookMapper;
	
	private String accessToken = "EAAcQxNJPHU4BRKyoMtBFZAJWChAN87kp0Egh0rSzuwnyFcY1hOO1czF0ZBVtTF43Jebhc3DVsNIJUaNzGuGfzndYk8u6VpOFYTaGneLkZBZAfrYk7nL9scmdOFGkhu04sZCNm22FUNZAeTD0VMZCzRxnwGZA6duTHtZCbyp6fWBbjmzZBUhybqS7DPDXuXvZByekKTVIpsXrzf7gKokFlT7WZCRG4JU55pVQx3zIYWZCpIbDqOQZDZD";
    
	@Transactional
	public void getSavePostfacebook() {
	    
	    PostFacebookResponseDto response = this.restTemplate.getForObject("https://graph.facebook.com/v25.0/me/posts?fields=id,created_time,story,message,comments&access_token=".concat(accessToken), PostFacebookResponseDto.class);
	
	    List<PostFacebookEntity> posts = this.facebookMapper.toEntityList(response.getData());
	    
	    List<PostFacebookEntity> nuevosPosts = posts.stream()
	    		.filter(post -> !this.postfacebookRepository.existsByfbPostId(post.getFbPostId()))
	    		.toList();

	    if(!nuevosPosts.isEmpty()) {
	    	this.postfacebookRepository.saveAll(posts);
	    }
	    
	}
	
	@Scheduled(fixedRate = 900000) // 15 minutos en milisegundos
	public void scheduleFacebookSync() {
	    System.out.println("Ejecutando sincronización automática...");
	    this.getSavePostfacebook();
	}

}
