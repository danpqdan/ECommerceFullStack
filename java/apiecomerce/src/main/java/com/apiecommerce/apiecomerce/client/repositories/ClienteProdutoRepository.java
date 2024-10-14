package com.apiecommerce.apiecomerce.client.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;

public interface ClienteProdutoRepository extends JpaRepository<ClienteProduto, Long> {

    @Query(value = "SELECT * FROM produto_cliente cp WHERE cp.sacola_id = :sacolaId", nativeQuery = true)
    List<ClienteProduto> encontreTodosOsClienteProdutoPorId(@Param("sacolaId") Long sacolaId);

}
