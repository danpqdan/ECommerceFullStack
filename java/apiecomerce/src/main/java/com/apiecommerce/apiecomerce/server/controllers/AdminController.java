package com.apiecommerce.apiecomerce.server.controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.DTO.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.services.SecurityFilter;
import com.apiecommerce.apiecomerce.server.services.TokenService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @Autowired
    SecurityFilter securityFilter;

    @GetMapping()
    public ResponseEntity<Void> getAdminData() {
        if (userHasAccess()) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Status 403 Forbidden
        }
    }

    private boolean userHasAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Verifique se o usuário possui a role ADMIN
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

}
