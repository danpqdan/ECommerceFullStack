package com.apiecommerce.apiecomerce.server.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.QuantidadeProdutoDTO;
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
    public List<Produtos> listarProduto() {
        return produtoRepository.findAll();
    }

    @PostMapping("/produtos")
    public ResponseEntity<String> adicionarProduto(
            @RequestParam("produto") String produtoJson,
            @RequestParam("imagem") MultipartFile imagem) {

        try {
            // Converte o JSON para o DTO
            ProdutoDTO produtoDTO = new ObjectMapper().readValue(produtoJson, ProdutoDTO.class);

            // Salva o produto e a imagem
            produtoService.saveProduto(produtoDTO, imagem);
            return ResponseEntity.ok("Produto adicionado com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar o produto e a imagem");
        }
    }

    @PostMapping("/produtos/quantidade")
    public ResponseEntity<List<Produtos>> atualizarQuantidadeProdutos(
            @RequestBody QuantidadeProdutoDTO quantidadeDeProdutos) {
        var a = produtoRepository.buscarProdutoPorQuantidade(quantidadeDeProdutos.quantidadeProdutos());
        List<Produtos> listaProduto = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            a.get(i).somaQuantidade(1);
            listaProduto.add(a.get(i));
        }
        produtoRepository.saveAllAndFlush(listaProduto);
        return ResponseEntity.ok().body(listaProduto);
    }
}
