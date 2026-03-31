package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostFacebookDto {

	@JsonProperty("id")
    private String fbPostId;
	
	private String created_time;
	
	private String story;
	
	private String message;
	
    private CommentsWrapperDto commentsWrapperDto;

}
