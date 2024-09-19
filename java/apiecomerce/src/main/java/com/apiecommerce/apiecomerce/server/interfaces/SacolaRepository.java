package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.Sacola;
import com.apiecommerce.apiecomerce.server.entities.Usuario;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {

    Optional<Sacola> findByUsuario(Usuario usuario);

    Optional<Sacola> findByProdutos(List<Produtos> produto);

}
