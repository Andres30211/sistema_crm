package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.CommentFacebookRepository;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.CommentfacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.CommentFacebookEntity;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity.PostFacebookEntity;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.mappers.FacebookMapper;

@Service
public class PostFacebookService {

	@Autowired
    private PostfacebookRepository postfacebookRepository;
	
	@Autowired
    private CommentFacebookRepository commentFacebookRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FacebookMapper facebookMapper;
	
	private String accessToken = "EAAcQxNJPHU4BRKdEsOWOKmmwM3bnwdUupVRTflZC7NMZBt4qp4hsNBvaeF0wNlxQ1epQWZBS7MqOZCrW05n6HDJ6ohfyYr90Mm0BxaOfVY4uZBDZAYYrHSJWcZBzqKvGotZBoWrMfZCRo5tgu4BeB5ZAojTwsNvd0KkKXtMOIQaV9ZCBabuLD2vH1cCzS1pknLSo3naZAfayIl9af3HWwThqlgAGZCFtmsS7OTTZCZC1NVNR69STgZDZD";
    
	@Transactional
	public void getSavePostfacebook() {
	    
	    PostFacebookResponseDto response = this.restTemplate.getForObject("https://graph.facebook.com/v25.0/me/posts?fields=id,created_time,story,message,comments&access_token=".concat(accessToken), PostFacebookResponseDto.class);
	
	    for (PostFacebookDto postDto : response.getData()) {

	        // 🔍 Buscar si el post ya existe
	        Optional<PostFacebookEntity> optionalPost =
	        		this.postfacebookRepository.findByFbPostId(postDto.getFbPostId());

	        PostFacebookEntity postEntity;

	        if (optionalPost.isPresent()) {
	            // 🔥 YA EXISTE → actualizar datos básicos
	            postEntity = optionalPost.get();
	            postEntity.setMessage(postDto.getMessage());
	            postEntity.setStory(postDto.getStory());

	        } else {
	            // 🔥 NUEVO POST
	            postEntity = facebookMapper.toEntity(postDto);
	        }

	        // 🔥 GUARDAR POST (nuevo o actualizado)
	        postEntity = this.postfacebookRepository.save(postEntity);

	        // 🔥 PROCESAR COMENTARIOS
	        if (postDto.getComments() != null && postDto.getComments().getData() != null) {

	            for (CommentfacebookDto commentDto : postDto.getComments().getData()) {

	                boolean exists = this.postfacebookRepository
	                        .existsByFbComentarioId(commentDto.getFbComentarioId());

	                if (!exists) {
	                    CommentFacebookEntity commentEntity = facebookMapper.toEntity(commentDto);
	                    commentEntity.setPost(postEntity); // relación

	                    this.commentFacebookRepository.save(commentEntity);
	                }
	            }
	        }
	    }
	    
	}
	
	@Scheduled(fixedRate = 900000) // 15 minutos en milisegundos
	public void scheduleFacebookSync() {
	    System.out.println("Ejecutando sincronización automática...");
	    this.getSavePostfacebook();
	}

}
