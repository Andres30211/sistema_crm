package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_facebook")
public class PostFacebookDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime fechaCaptura = LocalDateTime.now();
	
	private String created_time;
	
	private String story;
	
	private String message;

}
