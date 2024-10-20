package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Categoria;
import com.apiecommerce.apiecomerce.server.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(name = "SELECT p FROM Produto p WHERE p.categoria = :categoria", nativeQuery = true)
    List<Produto> findAllByCategoria(@Param("categoria") Categoria categoria);

    // @Query(value = "SELECT SUM(quantidade_em_sacola) FROM produto WHERE id =
    // :id", nativeQuery = true)
    // Integer somarQuantidadeProdutoEmTodasAsSacolasNative(@Param("id") Long
    // produtoId);

    // @Query("SELECT p FROM Produto p WHERE p.quantidadeEmSacola =
    // :quantidadeEmSacola")
    // List<Produto> buscarProdutoPorQuantidade(@Param("quantidadeEmSacola") Long
    // quantidadeEmSacola);

}
