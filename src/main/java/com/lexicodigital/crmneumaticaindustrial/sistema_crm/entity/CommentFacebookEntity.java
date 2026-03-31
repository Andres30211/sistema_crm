package com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "post_comments")
@Data
public class CommentFacebookEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntity;

    private String fbComentarioId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String createdTime;

    private String userName;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostFacebookEntity post;
}
