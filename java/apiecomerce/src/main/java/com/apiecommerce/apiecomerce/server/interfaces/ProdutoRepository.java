package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT SUM(quantidadeEmSacola) FROM sacola WHERE produto_id = :produtoId", nativeQuery = true)
    Integer somarQuantidadeProdutoEmTodasAsSacolasNative(@Param("produtoId") Long produtoId);

    @Query("SELECT p FROM Produto p WHERE p.quantidadeEmSacola = :quantidadeEmSacola")
    List<Produto> buscarProdutoPorQuantidade(@Param("quantidadeEmSacola") Long quantidadeEmSacola);

}
