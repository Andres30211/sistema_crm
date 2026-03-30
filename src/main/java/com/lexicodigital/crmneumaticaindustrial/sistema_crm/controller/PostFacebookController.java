package com.lexicodigital.crmneumaticaindustrial.sistema_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexicodigital.crmneumaticaindustrial.sistema_crm.dto.PostFacebookResponseDto;
import com.lexicodigital.crmneumaticaindustrial.sistema_crm.services.PostFacebookService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/facebook")
@RequiredArgsConstructor
public class PostFacebookController {
	
	@Autowired
	private PostFacebookService postFacebookService;

    @GetMapping("/user-info")
    public Mono<ResponseEntity<PostFacebookResponseDto>> getUserInfo() {
        return this.postFacebookService.getFacebookUserData()
            .map(userData -> ResponseEntity.ok(userData))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
 // --- SECCIÓN WEBHOOK ---

    // 1. Validación para Meta (Configúralo en el panel de Developers)
    @GetMapping("/webhook")
    public ResponseEntity<String> validarWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {
        
        // ESTO APARECERÁ EN LA PANTALLA NEGRA DE RAILWAY
        System.out.println(">>> PETICIÓN DE FACEBOOK RECIBIDA <<<");
        System.out.println("Modo recibido: " + mode);
        System.out.println("Token recibido: " + token);
        System.out.println("Challenge: " + challenge);

        // Verificación
        if ("subscribe".equals(mode) && "crm_neumatica_v1".equals(token)) {
            System.out.println("✅ TOKEN CORRECTO. Enviando respuesta a Facebook...");
            return ResponseEntity.ok(challenge);
        } else {
            System.out.println("❌ TOKEN INCORRECTO. Se esperaba 'crm_neumatica_v1' pero llegó: " + token);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
