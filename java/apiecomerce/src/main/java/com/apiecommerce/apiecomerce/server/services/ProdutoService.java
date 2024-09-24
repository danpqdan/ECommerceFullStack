package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.Sacola;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ImagensRepository;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ImagensRepository iRepository;
    @Autowired
    SacolaRepository sacolaRepository;

    public Produto retornarUmProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    public List<Produto> retornarListaProdutos(List<Long> id) {
        return produtoRepository.findAllById(id);
    }

    public int interarQuantidadeSacola(Produto produto, int quantidade) {
        if (produto == null) {
            return 0;
        }
        int quant = produto.getQuantidadeEmSacola();
        quant = quant + quantidade;
        produto.setQuantidadeEmSacola(quant);
        return quant;
    }

    public void saveProdutoComImagem(ProdutoDTO produtoDTO, MultipartFile imagemFile) throws IOException {
        // Cria e salva a imagem
        Imagens imagem = new Imagens();
        imagem.setNome(imagemFile.getOriginalFilename());
        imagem.setDados(imagemFile.getBytes());
        imagem = iRepository.save(imagem);

        // Cria e salva o produto
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNomeDoProduto());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeParaSacola(produto.getQuantidadeEmSacola());
        produto.setImagem(imagem);

        produtoRepository.save(produto);
    }

    public Produto saveProdutoSemImagem(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNomeDoProduto());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEmEstoque(produtoDTO.getQuantidadeEmEstoque());
        produto.setValorTotalEmEstoque(produtoDTO.getQuantidadeEmEstoque(), produtoDTO.getPreco());

        return produtoRepository.save(produto);
    }

    public List<Produto> atualizarTodosProdutos() {
        var produto = produtoRepository.findAll();
        for (int i = 0; i < produto.size(); i++) {
            var quantidade = produtoRepository.somarQuantidadeProdutoEmTodasAsSacolasNative(produto.get(i).getId());
            produto.get(i).setQuantidadeEmSacola(quantidade);
            produto.get(i).setValorTotalEmSacola(quantidade, produto.get(i).getPreco());
        }
        return produtoRepository.saveAll(produto);

    }

    public Produto atualizarProdutoPorId(ProdutoDTO dto) {
        var produto = produtoRepository.findById(dto.getId()).get();
        produto.setDescricao(dto.getDescricao());
        produto.setImagem(dto.getImagens());
        produto.setNome(dto.getNomeDoProduto());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEmSacola(dto.getQuantidadeEmSacola());
        return produtoRepository.save(produto);
    }

    @Transactional
    public String deletarProduto(long id) {
        var produto = produtoRepository.findById(id);
        List<Sacola> sacolas = sacolaRepository.findAllByProdutoId(id);
        for (Sacola sacola : sacolas) {
            sacola.getProduto().removeIf(p -> p.getId().equals(id));
            sacolaRepository.save(sacola);
        }

        if (produto.isPresent()) {
            produtoRepository.deleteById(id);
            return "Produto deletado " + produto.toString();
        }
        return "Produto não encontrado";

    }
}
