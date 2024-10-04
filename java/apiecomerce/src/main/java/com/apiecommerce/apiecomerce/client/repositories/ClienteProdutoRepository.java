package com.apiecommerce.apiecomerce.client.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;

public interface ClienteProdutoRepository extends JpaRepository<ClienteProduto, Long> {

    List<ClienteProduto> findAllBySacolaId(Long id);

}
