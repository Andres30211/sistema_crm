package com.lexicodigital.crmneumaticaindustrial.sistema_crm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebClientConfig {
	
	@Value("${token.access.posts.facebook}")
	private String tokenAccessPostsFacebook;
	
	@Value("${endpoint.access.posts.facebook}")
	private String endpointAccessPostsFacebook;
	
	@Bean
	public String tokenAccessPostFacebook() {
		return this.tokenAccessPostsFacebook;
	}
	
	@Bean
	public String endpointAccessPostFacebook() {
		return this.tokenAccessPostsFacebook;
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	
}
