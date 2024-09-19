package com.apiecommerce.apiecomerce.server.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.Sacola;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaDTO;
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

    // Necessario implementar logica para resgate de ID de pagamento
    // Necessario Criar tabela adicional para guardar informacoes
    public boolean verificaSituacaoSacola(Long id) {
        var sacola = sacolaRepository.findById(id).get();
        boolean situacao = true;
        if (sacola.getEstadoDaCompra() == EstadoDaCompra.ENTREGUE) {
            sacolaRepository.delete(sacola);
            situacao = false;
        }
        return situacao;
    }

    public void removeItem(ProdutoDTO produtoDTO) {
        produtoRepository.deleteById(produtoDTO.getId());
    }

    public Sacola novaSacola(SacolaDTO sacolaDTO) {
        Usuario usuario = usuarioRepository.findById(sacolaDTO.getUsuarioID()).orElseThrow();
        List<Produtos> produto = produtoRepository // Lista produtos apartir de DTO
                .findAllById(sacolaDTO.getProdutos().stream().map(ProdutoDTO::getId).collect(Collectors.toList()));
        var verificaSacola = sacolaRepository.findByUsuario(usuario).isPresent(); // Verifica se o Usuario tem sacola
        if (verificaSacola == true) {
            Sacola sacolaAnt = sacolaRepository.findByUsuario(usuario).get(); // instancia sacola apartir de usuario
            if (verificaSituacaoSacola(sacolaAnt.getId())) {
                sacolaAnt.setProdutos(produto);
            }
            var dto = sacolaDTO.getProdutos().stream()
                    .sorted(Comparator.comparing(ProdutoDTO::getId))
                    .collect(Collectors.toList());
            for (var i = 0; i < produto.size(); i++) {
                var quantidade = produto.get(i).getQuantidadeEmSacola();
                var preco = produto.get(i).getPreco();
                var quantidadeDto = dto.get(i).getQuantidadeDisponivel();
                produto.get(i).setValorTotal(preco, quantidade);
                produto.get(i).setQuantidadeEmSacola(quantidade + quantidadeDto);
            }
            var lProdutos = sacolaAnt.getProdutos();
            sacolaAnt.setValorTotalSacola(lProdutos);
            sacolaRepository.save(sacolaAnt);
            return sacolaAnt;
        }

        Sacola sacola = new Sacola();
        sacola.setUsuario(usuario);
        sacola.setEstadoDaCompra(EstadoDaCompra.PENDENTE);
        sacola.setProdutos(produto);
        sacola.setValorTotalSacola(produto);
        sacolaRepository.save(sacola);
        return sacola;
    }

    public Sacola adicionarProdutos(SacolaDTO dto) {
        var sacola = sacolaRepository.findById(dto.getSacolaID()).get();
        var produtos = produtoRepository
                .findAllById(dto.getProdutos().stream().map(ProdutoDTO::getId).collect(Collectors.toList()));
        sacola.setValorTotalSacola(produtos);
        sacolaRepository.save(sacola);
        return sacola;
    }

    public List<Sacola> todasSacolas() {
        return sacolaRepository.findAll();
    }

    public Sacola listarSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow();
    }

}
