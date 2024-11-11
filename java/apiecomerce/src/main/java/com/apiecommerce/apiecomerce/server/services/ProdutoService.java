package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiecommerce.apiecomerce.server.entities.Categoria;
import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.ServerProduto;
import com.apiecommerce.apiecomerce.server.entities.data.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.CategoriaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ImagensRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ServerProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ImagensRepository iRepository;
    @Autowired
    SacolaRepository sacolaRepository;
    @Autowired
    ServerProdutoRepository serverProdutoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    public ServerProduto retornarUmProduto(Long id) {
        return serverProdutoRepository.findById(id).orElseThrow();
    }

    public List<ServerProduto> listarProduto() {
        return serverProdutoRepository.findAll();
    }

    public void saveProdutoComImagem(ProdutoDTO produtoDTO) throws IOException {

        Imagens imagem = new Imagens();
        imagem.setNome(produtoDTO.getImagemDTO().getNome());
        imagem.setDescricao(produtoDTO.getImagemDTO().getDescricao());
        imagem.setUrlPrincipal(produtoDTO.getImagemDTO().getUrlPrincipal());
        imagem.setUrlMiniatura(produtoDTO.getImagemDTO().getUrlMiniatura());
        imagem.setUrlMiniatura2(produtoDTO.getImagemDTO().getUrlMiniatura2());
        imagem.setUrlMiniatura3(produtoDTO.getImagemDTO().getUrlMiniatura3());
        imagem.setUrlMiniatura4(produtoDTO.getImagemDTO().getUrlMiniatura4());

        Produto produto = new ServerProduto();
        produto.setNome(produtoDTO.getNomeDoProduto());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(imagem);
        var categoria = categoriaRepository.findByCategoria(produtoDTO.getCategoria().getCategoria());
        if (categoria.isEmpty()) {
            Categoria novaCategoria = new Categoria(produtoDTO.getCategoria().getCategoria());
            novaCategoria = categoriaRepository.save(novaCategoria);
            produto.setCategoria(novaCategoria);
        }
        produto.setCategoria(categoria.get());

        ServerProduto serverProduto = new ServerProduto();
        serverProduto.setNome(produtoDTO.getNomeDoProduto());
        serverProduto.setDescricao(produtoDTO.getDescricao());
        serverProduto.setPreco(produtoDTO.getPreco());
        serverProduto.setQuantidadeEmEstoque(produtoDTO.getQuantidadeEmEstoque());
        produto.setCategoria(categoria.get());

        iRepository.save(imagem);
        produtoRepository.save(produto);
    }

    @Transactional
    public List<ServerProduto> saveProdutoSemImagem(List<ServerProduto> produtoDTO) {
        List<Produto> produtos = new ArrayList<>();
        List<ServerProduto> serverProdutos = new ArrayList<>();
        for (ServerProduto p1 : produtoDTO) {
            ServerProduto serverProduto = new ServerProduto();
            serverProduto.setNome(p1.getNome());
            serverProduto.setDescricao(p1.getDescricao());
            serverProduto.setPreco(p1.getPreco());
            serverProduto.setQuantidadeEmEstoque(p1.getQuantidadeEmEstoque());
            serverProduto.setValorTotalEmEstoque();
            serverProdutos.add(serverProduto);
            produtos.add(serverProduto);
        }
        // serverProdutoRepository.saveAll(serverProdutos);
        produtoRepository.saveAll(produtos);
        return serverProdutos;

    }

}
