package com.apiecommerce.apiecomerce.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.Roles;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    SacolaRepository sacolaRepository;
    @Autowired
    SecurityFilter securityFilter;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO loginDeUsuario(AuthenticationDTO login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        var tokeRole = tokenService.extractUserRoles(token);
        return new LoginResponseDTO(token, tokeRole);

    }

    public String novoUsuario(AuthenticationDTO login) {
        if (usuarioRepository.findByUsername(login.getUsername()) != null)
            return "Usuario ja existe";

        String encryptedPassword = new BCryptPasswordEncoder().encode(login.getPassword());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPassword(encryptedPassword);
        novoUsuario.setUsername(login.getUsername());
        novoUsuario.setRole(Roles.USER);

        SacolaCliente sacola = new SacolaCliente(novoUsuario);

        novoUsuario.setSacola(sacola);

        sacola.setUsuario(novoUsuario);
        sacola.setEstadoDaCompra(EstadoDaCompra.PENDENTE);

        usuarioRepository.save(novoUsuario);
        // sacolaRepository.save(sacola);
        return "Usuario registrado";
    }

    public String transformAdminRole(AuthenticationDTO login) {
        Usuario usuario = usuarioRepository.encontrarByUsername(login.getUsername());
        usuario.setRole(Roles.ADMIN);
        usuarioRepository.save(usuario);
        return "Usuario atualizado";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public UserDetails validarUsuarioPorToken(String token, AuthenticationDTO login) {
        var userToken = tokenService.extrairUsuario(token);
        var usuario = validarUsuario(login);
        if (userToken.equals(usuario.getUsername())) {
            var user = loadUserByUsername(login.getUsername());
            return user;
        }
        return null;
    }

    public Usuario validarUsuario(AuthenticationDTO login)
            throws UsernameNotFoundException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var usuario = usuarioRepository.findByUsername(login.getUsername());
        if (usuario.getUsername().equals(login.getUsername())
                && passwordEncoder.matches(login.getPassword(), usuario.getPassword())) {
            return usuarioRepository.encontrarByUsername(login.getUsername());
        }
        return null;
    }

    public boolean testeUsuario(AuthenticationDTO login) {
        boolean teste = true;
        var a = validarUsuario(login);
        if (a == null) {
            return teste = false;
        }
        return teste;

    }

}
