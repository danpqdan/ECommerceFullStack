package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.ServerProduto;
import com.apiecommerce.apiecomerce.server.entities.data.ProdutoDTO;
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

    public ServerProduto retornarUmProduto(Long id) {
        return serverProdutoRepository.findById(id).orElseThrow();
    }

    public List<ServerProduto> listarProduto() {
        return serverProdutoRepository.findAll();
    }

    @Transactional
    public void saveProdutoComImagem(ProdutoDTO produtoDTO, MultipartFile imagemFile) throws IOException {
        // Cria e salva a imagem
        Imagens imagem = new Imagens();
        imagem.setNome(imagemFile.getOriginalFilename());
        imagem.setDados(imagemFile.getBytes());

        Produto produto = new ServerProduto();
        produto.setNome(produtoDTO.getNomeDoProduto());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(imagem);

        ServerProduto serverProduto = new ServerProduto();
        serverProduto.setNome(produtoDTO.getNomeDoProduto());
        serverProduto.setDescricao(produtoDTO.getDescricao());
        serverProduto.setPreco(produtoDTO.getPreco());
        serverProduto.setQuantidadeEmEstoque(produtoDTO.getQuantidadeEmEstoque());

        //serverProdutoRepository.save(serverProduto);
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
        //serverProdutoRepository.saveAll(serverProdutos);
        produtoRepository.saveAll(produtos);
        return serverProdutos;

    }

}
