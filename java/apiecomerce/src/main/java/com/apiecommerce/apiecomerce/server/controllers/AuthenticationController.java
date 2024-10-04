package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.Roles;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
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
    @Autowired
    private SacolaRepository sacolaRepository;
    @Autowired
    ClienteSacolaRepository sacolaRepository2;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthenticationDTO data) {
        if (repository.findByUsername(data.getUsername()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Usuario newUser = new Usuario();
        newUser.setPassword(encryptedPassword);
        newUser.setUsername(data.getUsername());
        newUser.setRole(Roles.USER);

        SacolaCliente sacola = new SacolaCliente(newUser);

        newUser.setSacola(sacola);

        sacola.setUsuario(newUser);
        sacola.setEstadoDaCompra(EstadoDaCompra.PENDENTE);

        repository.save(newUser);
        sacolaRepository.save(sacola);

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
