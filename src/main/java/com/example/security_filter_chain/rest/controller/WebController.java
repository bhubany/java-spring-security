package com.example.security_filter_chain.rest.controller;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security_filter_chain.configuration.AppConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebController {
    private final AppConfiguration appconfig;

    @GetMapping("/private")
    public ResponseEntity privateMethod(Authentication authentication) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(details);
    }

    @GetMapping("/public")
    public ResponseEntity publicMethod() {
        return ResponseEntity.ok("Public access success");
    }

    @GetMapping("/default")
    public ResponseEntity defaultMethod() {
        System.out.println("################# From controller");
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getDetails());
    }

    @GetMapping("/auth")
    public ResponseEntity authMethod(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
                .from(appconfig.getCookie().getName(), "a63587c8-cdcb-4ab1-94cc-51538c4f698d")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(appconfig.getCookie().getExpiresIn())
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Authentication Success");
    }
}
