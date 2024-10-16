package com.apiecommerce.apiecomerce.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.services.ProdutoService;
import com.apiecommerce.apiecomerce.server.services.SacolaService;

@RestController
@RequestMapping("/api/sacola")
public class SacolaController {

    @Autowired
    SacolaService sacolaService;
    @Autowired
    ProdutoService produtoService;

    // @GetMapping
    // public ResponseEntity sacolas() {
    //     return ResponseEntity.ok().body(sacolaService.todaSacolas());
    // }

    // @PostMapping
    // public ResponseEntity iniciarNovaSacola(@RequestBody SacolaDTO sacolaDTO) {
    //     var result = sacolaService.adicionarProdutoASacola(sacolaDTO);
    //     return ResponseEntity.ok().body(result);
    // }

    // @GetMapping("/{sacola}")
    // public ResponseEntity sacolaPorID(@PathVariable("sacola") long sacola) {
    //     return ResponseEntity.ok().body(sacolaService.sacolaPorId(sacola));
    // }

    // @GetMapping("/{sacola}/produtos")
    // public ResponseEntity produtosPorSacola(@PathVariable("sacola") long sacola) {
    //     return ResponseEntity.ok().body(sacolaService.regatarTodosOsProdutosDaSacola(sacola));
    // }

}
