package com.apiecommerce.apiecomerce.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.DTO.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario validarUsuario(AuthenticationDTO login) throws UsernameNotFoundException {
        UserDetails uDetails = loadUserByUsername(login.username());
        if (uDetails.getPassword() != login.password()) {
            System.out.println("Usuario não encontrado");
        }
        return usuarioRepository.encontrarByUsername(login.username());
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
