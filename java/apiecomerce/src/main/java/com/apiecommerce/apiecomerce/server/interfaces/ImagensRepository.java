package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Imagens;

@Repository
public interface ImagensRepository extends JpaRepository<Imagens, Long> {

    @Query("SELECT p FROM Imagens p WHERE p.descricao = :descricao")
    List<Imagens> findByDescricao(@Param("descricao") String descricao);

}
