package com.apiecommerce.apiecomerce.server.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.ServerProduto;
import com.apiecommerce.apiecomerce.server.entities.data.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;

    @GetMapping("/produtos")
    public List<ServerProduto> listarProduto() {
        return produtoService.listarProduto();
    }

    @PostMapping("/produtoeimagem")
    public ResponseEntity<String> adicionarProdutoComImagem(
            @RequestParam("produto") String produtoJson,
            @RequestParam("imagem") MultipartFile imagem) {

        try {
            // Converte o JSON para o DTO
            ProdutoDTO produtoDTO = new ObjectMapper().readValue(produtoJson, ProdutoDTO.class);

            // Salva o produto e a imagem
            produtoService.saveProdutoComImagem(produtoDTO, imagem);
            return ResponseEntity.ok("Produto adicionado com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar o produto e a imagem");
        }
    }

    @PostMapping("/produto")
    public ResponseEntity<List<ServerProduto>> adicionarProdutoSemImagem(@RequestBody List<ServerProduto> produto) {
        var result = produtoService.saveProdutoSemImagem(produto);
        return ResponseEntity.ok().body(result);
    }
}
