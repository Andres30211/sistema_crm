package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Data
@Entity
@Table(name = "post_facebook")
public class PostFacebookDto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
    private Long id;

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

}
