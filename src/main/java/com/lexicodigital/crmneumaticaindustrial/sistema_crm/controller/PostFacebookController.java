package com.lexicodigital.crmneumaticaindustrial.sistema_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.services.PostFacebookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/facebook")
@RequiredArgsConstructor
public class PostFacebookController {
	
	@Autowired
	private PostFacebookService postFacebookService;

    @GetMapping("/seve-posts")
    public void getUserInfo() {
        this.postFacebookService.getSavePostfacebook();
    }
    
}
