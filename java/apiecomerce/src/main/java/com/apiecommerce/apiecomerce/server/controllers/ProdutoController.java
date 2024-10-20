package com.apiecommerce.apiecomerce.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.server.entities.Categoria;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.ServerProduto;
import com.apiecommerce.apiecomerce.server.entities.data.CategoriaDTO;
import com.apiecommerce.apiecomerce.server.entities.data.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.CategoriaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ServerProdutoRepository;
import com.apiecommerce.apiecomerce.server.services.ProdutoService;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ServerProdutoRepository serverProdutoRepository;

    @GetMapping("/produtos")
    public List<ServerProduto> listarProduto() {
        return produtoService.listarProduto();
    }

    @GetMapping("/categoria/produtos")
    public ResponseEntity<List<ServerProduto>> listarProdutosPorCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findByCategoria(categoriaDTO.getCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        List<ServerProduto> produtos = serverProdutoRepository.findAllByCategoria(categoria);
        return ResponseEntity.ok().body(produtos);

    }

    @PostMapping("/produtoeimagem")
    public ResponseEntity<String> adicionarProdutoComImagem(@RequestBody ProdutoDTO produtoDTO) {
        try {
            produtoService.saveProdutoComImagem(produtoDTO);
            return ResponseEntity.ok("Produto adicionado com sucesso");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar o produto e a imagem");
        }

    }

    @PostMapping("/produtos")
    public ResponseEntity<List<ServerProduto>> adicionarProdutoSemImagem(@RequestBody List<ServerProduto> produto) {
        var result = produtoService.saveProdutoSemImagem(produto);
        return ResponseEntity.ok().body(result);
    }
}
