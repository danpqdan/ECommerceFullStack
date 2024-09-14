package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.Roles;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.DTO.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.RegisterDTO;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;
import com.apiecommerce.apiecomerce.server.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (repository.findByUsername(data.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario();
        newUser.setPassword(encryptedPassword);
        newUser.setUsername(data.username());
        newUser.setRole(data.role().USER);

        repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/register/{id}")
    public ResponseEntity registerAdmin(@RequestBody LoginResponseDTO loginResponseDTO, @PathVariable Long id) {
        Usuario usuario = repository.findById(id).get();
        usuario.setRole(Roles.ADMIN);
        repository.saveAndFlush(usuario);
        return ResponseEntity.ok().body(usuario.getRole());
    }

}
