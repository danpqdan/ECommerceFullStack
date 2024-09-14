package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.DTO.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaProdutoDTO;
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
    public ResponseEntity novaSacola(@RequestBody AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok().body(sacolaService.novaSacola(authenticationDTO));
    }

    @PostMapping("/{sacola}") // Rota dinamica de acordo com a sacola do usuario *IMPLEMENTAR*
    public ResponseEntity adicionarProduto(@RequestBody SacolaProdutoDTO sacolaProdutoDTO, @PathVariable Long sacola) {
        if (sacola == sacolaProdutoDTO.getSacolaID()) {
            produtoService.retornarUmProduto(sacolaProdutoDTO);
            sacolaService.adicionarProdutos(sacolaProdutoDTO);
            sacolaService.somaQuantidadeProduto(sacolaProdutoDTO.getQuantidade(), sacolaProdutoDTO.getProdutoID());
            sacolaService.somaValorProdutos(sacolaProdutoDTO);
            return ResponseEntity.ok()
                    .body(produtoService.retornarUmProduto(sacolaProdutoDTO).getNome() + "Adicionado a Sacola");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{sacola}")
    public ResponseEntity sacolaPorID(@PathVariable("sacola") long sacola) {
        return ResponseEntity.ok().body(sacolaService.listarSacola(sacola));
    }

}
