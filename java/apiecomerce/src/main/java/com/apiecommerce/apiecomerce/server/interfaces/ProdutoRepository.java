package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

    @Query("SELECT p FROM Produtos p WHERE p.quantidade = :quantidade")
    List<Produtos> buscarProdutoPorQuantidade(@Param("quantidade") Integer quantidade);

}
