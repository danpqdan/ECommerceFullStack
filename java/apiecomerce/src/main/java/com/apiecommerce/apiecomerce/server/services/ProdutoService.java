package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ImagensRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ImagensRepository iRepository;

    public Produtos retornarUmProduto(SacolaProdutoDTO sacolaProdutoDTO) {
        return produtoRepository.findById(sacolaProdutoDTO.getProdutoID()).orElseThrow();
    }

    public void saveProduto(ProdutoDTO produtoDTO, MultipartFile imagemFile) throws IOException {
        // Cria e salva a imagem
        Imagens imagem = new Imagens();
        imagem.setNome(imagemFile.getOriginalFilename());
        imagem.setDados(imagemFile.getBytes());
        imagem = iRepository.save(imagem);

        // Cria e salva o produto
        Produtos produto = new Produtos();
        produto.setNome(produtoDTO.getNomeDoProduto());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(imagem);

        produtoRepository.save(produto);
    }
}
