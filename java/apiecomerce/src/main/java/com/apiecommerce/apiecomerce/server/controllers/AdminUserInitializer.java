package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.apiecommerce.apiecomerce.server.entities.Roles;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;

@Configuration
public class AdminUserInitializer {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner createAdminUser() {
        return args -> {
            if (userRepository.encontrarByUsername(adminUsername) == null) {
                Usuario adminUser = new Usuario();
                adminUser.setUsername(adminUsername);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                adminUser.setRole(Roles.ADMIN);
                userRepository.save(adminUser);

                System.out.println("Usuário administrador criado com sucesso!");
            } else {
                System.out.println("Usuário administrador já existe.");
            }
        };
    }
}
