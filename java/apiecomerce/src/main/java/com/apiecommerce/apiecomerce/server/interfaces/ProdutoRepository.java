package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

    @Query("SELECT p FROM Produtos p WHERE p.quantidadeEmSacola = :quantidadeEmSacola")
    List<Produtos> buscarProdutoPorQuantidade(@Param("quantidadeEmSacola") Integer quantidadeEmSacola);

}
