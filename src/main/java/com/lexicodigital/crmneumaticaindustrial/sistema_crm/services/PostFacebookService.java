/*package com.lexicodigital.crmneumaticaindustrial.sistema_crm.services;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.CommentFacebookRepository;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.repository.PostfacebookRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.postsandcommentsfacebook.CommentfacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.postsandcommentsfacebook.PostFacebookDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.postsandcommentsfacebook.PostFacebookResponseDto;
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
	
	@Autowired
	private String tokenAccessPostFacebook;
	
	@Autowired
	private String endpointAccessPostFacebook;
    
	@Transactional
	public void getSavePostfacebook() {
	    
	    PostFacebookResponseDto response = this.restTemplate.getForObject(this.endpointAccessPostFacebook.concat(this.tokenAccessPostFacebook), PostFacebookResponseDto.class);
	
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

	                boolean exists = this.commentFacebookRepository
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
		try {
			System.out.println("Ejecutando sincronización automática...");
		    this.getSavePostfacebook();
		} catch (Exception e) {
			System.out.println("Error en la llamada del servicio: ".concat(e.getMessage()));
		}
	    
	}

}*/
