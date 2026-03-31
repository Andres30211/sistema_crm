package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_comments")
public class CommentfacebookDto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonProperty(value = "id")
    private String fbComentarioId; // ID que viene de Facebook
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostFacebookDto post;
}
