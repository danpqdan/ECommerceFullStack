package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.services.CustomUserDetailsService;
import com.apiecommerce.apiecomerce.server.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        return ResponseEntity.ok().body(userDetailsService.loginDeUsuario(data));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthenticationDTO data) {
        return ResponseEntity.ok().body(userDetailsService.novoUsuario(data));
    }

    @PutMapping("/register/{id}")
    public ResponseEntity registerAdmin(@RequestHeader("Authorization") String token,
            @RequestBody AuthenticationDTO login) {
        var username = tokenService.extrairUsuario(token);
        if (username == login.getUsername()) {
            return ResponseEntity.ok().body(userDetailsService.transformAdminRole(login));
        }
        return ResponseEntity.badRequest().build();
    }

}
