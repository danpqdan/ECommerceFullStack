package com.apiecommerce.apiecomerce.server.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario encontrarByUsername(@Param("username") String username);

    // Optional<Usuario> loadByUsername(String username);

}
