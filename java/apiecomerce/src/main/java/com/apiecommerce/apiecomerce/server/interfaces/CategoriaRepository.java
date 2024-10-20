package com.apiecommerce.apiecomerce.server.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiecommerce.apiecomerce.server.entities.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<List<Categoria>> findAllByCategoria(String categoria);

    Optional<Categoria> findByCategoria(String categoria);

    // Optional<List<Categoria>> findAllCategoriaByCategoria(String categoria);

}
