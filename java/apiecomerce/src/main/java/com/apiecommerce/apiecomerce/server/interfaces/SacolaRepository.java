package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;

@Repository
public interface SacolaRepository extends JpaRepository<SacolaServer, Long> {

    SacolaServer findByUsuarioId(Long id);

    // List<Sacola> findAllByProdutoId(Long produtoId);

    //Optional<Sacola> findByUsuarioId(Long usuarioId);

    // Optional<Sacola> findByProduto(List<SacolaProduto> produto);

}
