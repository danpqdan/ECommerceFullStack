package com.apiecommerce.apiecomerce.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteProdutoRepository;
import com.apiecommerce.apiecomerce.client.services.ClienteProdutoService;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;

@Service
public class SacolaService {

    @Autowired
    ClienteProdutoService ClienteProdutoService;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ClienteProdutoRepository clienteProdutoRepository;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    SacolaRepository sacolaRepository;

    public void valorFinal(ClienteSacolaDTO cliente) {
        Usuario usuario = userDetailsService.validarUsuario(cliente.getLogin());
        List<ClienteProduto> clienteProdutos = clienteProdutoRepository
                .encontreTodosOsClienteProdutoPorId(usuario.getSacola().getId());
        var valorTotal = clienteProdutos.stream().mapToDouble(ClienteProduto::getValorTotalDeProduto).sum();
        usuario.getSacola().setValorFinal(valorTotal);
        sacolaRepository.saveAndFlush(usuario.getSacola());
    }

}
