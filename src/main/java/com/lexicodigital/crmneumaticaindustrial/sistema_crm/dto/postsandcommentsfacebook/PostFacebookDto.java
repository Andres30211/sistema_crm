package com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.postsandcommentsfacebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostFacebookDto {

	@JsonProperty("id")
    private String fbPostId;
	
	private String created_time;
	
	private String story;
	
	private String message;
	
	@JsonProperty("comments")
    private CommentsWrapperDto comments;

}
