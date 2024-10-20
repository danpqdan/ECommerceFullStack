package com.apiecommerce.apiecomerce.server.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.data.CategoriaDTO;
import com.apiecommerce.apiecomerce.server.interfaces.CategoriaRepository;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        var lista = categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaDTO(categoria.getCategoria()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(lista);
    }
}
