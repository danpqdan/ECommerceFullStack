package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ImagensRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ImagensRepository iRepository;

    public Produtos retornarUmProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    public List<Produtos> retornarListaProdutos(List<Long> id) {
        return produtoRepository.findAllById(id);
    }

    public int interarQuantidadeSacola(Produtos produto, int quantidade) {
        if (produto == null) {
            return 0;
        }
        int quant = produto.getQuantidadeEmSacola();
        quant = quant + quantidade;
        produto.setQuantidadeEmSacola(quant);
        return quant;
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
        produto.setQuantidadeParaSacola(produto.getQuantidadeEmSacola());
        produto.setImagem(imagem);

        produtoRepository.save(produto);
    }

    public Produtos atualizarProduto(ProdutoDTO dto) {
        var produto = produtoRepository.findById(dto.getId()).get();
        produto.setDescricao(dto.getDescricao());
        produto.setImagem(dto.getImagens());
        produto.setNome(dto.getNomeDoProduto());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEmSacola(dto.getQuantidadeDisponivel());
        return produtoRepository.save(produto);

    }
}
