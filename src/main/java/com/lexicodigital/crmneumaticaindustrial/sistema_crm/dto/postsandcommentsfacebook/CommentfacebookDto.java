package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.postsandcommentsfacebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommentfacebookDto {

	@JsonProperty("id")
    private String fbComentarioId;
    
    private String message;

    @JsonProperty("from")
    private FromFacebookDto from;
}
