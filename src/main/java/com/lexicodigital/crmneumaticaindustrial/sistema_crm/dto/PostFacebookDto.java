package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Data
@Entity
@Table(name = "post_facebook")
public class PostFacebookDto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntity;

	@JsonProperty(value = "id")
    @Column(unique = true)
    private String fbPostId;
	
	private LocalDateTime fechaCaptura = LocalDateTime.now();
	
	private String created_time;
	
	@Column(columnDefinition = "TEXT")
	private String story;
	
	@Column(columnDefinition = "TEXT")
	private String message;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentfacebookDto> comentarios = new ArrayList();
	
	// --- EL TRUCO PARA EL JSON DE FACEBOOK ---
    @JsonProperty("comments")
    private void unpackComments(Map<String, Object> comments) {
        if (comments != null && comments.containsKey("data")) {
            ObjectMapper mapper = new ObjectMapper();
            // Esto convierte lo que está dentro de "data" en tu lista de DTOs
            this.comentarios = mapper.convertValue(
                comments.get("data"), 
                new TypeReference<List<CommentfacebookDto>>() {}
            );
            // Asignamos este post a cada comentario para la relación JPA
            this.comentarios.forEach(c -> c.setPost(this));
        }
    }

}
