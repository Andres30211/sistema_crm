package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_facebook")
public class PostFacebookDto {
	
	@Id
	private String id;
	
	private LocalDateTime fechaCaptura = LocalDateTime.now();
	
	private String created_time;
	
	@Column(columnDefinition = "TEXT")
	private String story;
	
	@Column(columnDefinition = "TEXT")
	private String message;

}
