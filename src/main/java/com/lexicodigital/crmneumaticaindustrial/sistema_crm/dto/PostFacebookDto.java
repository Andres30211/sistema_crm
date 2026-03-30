package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "post_facebook")
public class PostFacebookDto {
	
	@Id	
	private String id;
	
	private LocalDateTime fechaCaptura = LocalDateTime.now();
	
	private String created_time;
	
	private String story;
	
	private String message;
	
	private String likes;
	
	private String comments;

}
