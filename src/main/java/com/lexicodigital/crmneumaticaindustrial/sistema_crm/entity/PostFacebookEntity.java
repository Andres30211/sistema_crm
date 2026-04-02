/*package com.lexicodigital.crmneumaticaindustrial.sistema_crm.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "post_facebook")
@Data
public class PostFacebookEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntity;

    @Column(unique = true)
    private String fbPostId;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String story;

    private String createdTime;

    private LocalDateTime fechaCaptura;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentFacebookEntity> comentarios;
}*/
