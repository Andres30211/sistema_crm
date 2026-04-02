package com.lexicodigital.crmneumaticaindustrial.sistema_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/facebook")
@RequiredArgsConstructor
public class PostFacebookController {
	
	@GetMapping("/webhook")
	public String verify(
	        @RequestParam("hub.mode") String mode,
	        @RequestParam("hub.challenge") String challenge,
	        @RequestParam("hub.verify_token") String token) {

	    if ("TU_TOKEN".equals(token)) {
	        return challenge;
	    }
	    return "error";
	}
	
	@PostMapping("/webhook")
	public ResponseEntity<Void> receive(@RequestBody String payload) {

	    System.out.println("🔥 EVENTO:");
	    System.out.println(payload);

	    return ResponseEntity.ok().build();
	}
    
}
