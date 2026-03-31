package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

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
