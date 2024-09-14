package com.apiecommerce.apiecomerce.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.Sacola;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.DTO.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;

@Service
public class SacolaService {

    @Autowired
    SacolaRepository sacolaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public boolean verificaSacolaExistente(Long id) {
        boolean teste = true;
        if (sacolaRepository.findById(id).isPresent()) {
            return teste = false;
        }
        return teste;
    }

    public Double somaValorProdutos(SacolaProdutoDTO dto) {
        Sacola sacola = listarSacola(dto.getSacolaID());
        var total = sacola.getProdutos().stream().mapToDouble(Produtos::getPreco).sum();
        var totalValor = total * dto.getQuantidade();
        sacola.setValorFinal(totalValor);
        sacolaRepository.saveAndFlush(sacola);
        return sacola.getValorFinal();
    }

    public void somaQuantidadeProduto(int quantidade, Long id) {
        var quantidadeAtual = +quantidade;
        var produtoDB = produtoRepository.findById(id).get().getQuantidade();
        var sunFinal = produtoDB + quantidadeAtual;
        produtoRepository.findById(id).get().setQuantidade(sunFinal);
    }

    public void removeItem(ProdutoDTO produtoDTO) {
        produtoRepository.deleteById(produtoDTO.getId());
    }

    public ResponseEntity novaSacola(AuthenticationDTO login) {
        var user = usuarioRepository.encontrarByUsername(login.username());
        if (customUserDetailsService.testeUsuario(login) != true) {
            return ResponseEntity.notFound().build();
        }
        Sacola sacola = new Sacola(user);
        sacolaRepository.save(sacola);
        return ResponseEntity.accepted().body(sacola);
    }

    public ResponseEntity adicionarProdutos(SacolaProdutoDTO dto) {
        Produtos produto = produtoRepository.findById(dto.getProdutoID()).orElseThrow();
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioID()).orElseThrow();
        Sacola sacola = sacolaRepository.findByUsuario(usuario);
        try {
            sacola.addProduto(produto);
            return ResponseEntity.accepted().body(sacolaRepository.saveAndFlush(sacola));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.fillInStackTrace());
        }
    }

    public List<Sacola> todasSacolas() {
        return sacolaRepository.findAll();
    }

    public Sacola listarSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow();
    }

}
