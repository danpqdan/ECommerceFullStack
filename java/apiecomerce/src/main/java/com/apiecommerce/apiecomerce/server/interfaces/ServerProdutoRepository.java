package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Categoria;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.ServerProduto;

@Repository
public interface ServerProdutoRepository extends JpaRepository<ServerProduto, Long> {

    @Query(name = "SELECT p FROM ServerProduto p WHERE p.categoria = :categoria", nativeQuery = true)
    List<ServerProduto> findAllByCategoria(@Param("categoria") Categoria categoria);

}
