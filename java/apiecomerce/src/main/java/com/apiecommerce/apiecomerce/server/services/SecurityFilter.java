package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);
        if (token != null) {
            var usuario = tokenService.extrairUsuario(token);
            if (tokenService.validateTokenFromUsername(token, usuario)) {

                var login = tokenService.validateToken(token);
                UserDetails user = usuarioRepository.findByUsername(login);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                UserDetails u1 = new Usuario();
                var authentication = new UsernamePasswordAuthenticationToken(u1, null, u1.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }

}