package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaDTO;
import com.apiecommerce.apiecomerce.server.services.ProdutoService;
import com.apiecommerce.apiecomerce.server.services.SacolaService;

@RestController
@RequestMapping("/api/sacola")
public class SacolaController {

    @Autowired
    SacolaService sacolaService;
    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public ResponseEntity sacolas() {
        return ResponseEntity.ok().body(sacolaService.todasSacolas());
    }

    @PostMapping
    public ResponseEntity iniciarNovaSacola(@RequestBody SacolaDTO listaProduto) {
        var result = sacolaService.novaSacola(listaProduto);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{sacola}")
    public ResponseEntity adicionarProdutos(@RequestBody SacolaDTO sacolaProdutoDTO,
            @PathVariable Long sacolaID) {
        if (sacolaID == sacolaProdutoDTO.getSacolaID()) {
            var sacola = sacolaService.adicionarProdutos(sacolaProdutoDTO);
            return ResponseEntity.ok()
                    .body(sacola);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{sacola}")
    public ResponseEntity sacolaPorID(@PathVariable("sacola") long sacola) {
        return ResponseEntity.ok().body(sacolaService.listarSacola(sacola));
    }

}
