package com.apiecommerce.apiecomerce.client.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteProdutoRepository;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.services.ProdutoService;

@Service
public class ClienteProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    ClienteProdutoRepository clienteProdutoRepository;
    @Autowired
    ClienteSacolaRepository sacolaRepository;

    public List<ClienteProduto> retornarProdutos(Long idSacola) {
        return clienteProdutoRepository.findAllBySacolaId(idSacola);
    }

    public List<ClienteProduto> retornarProdutoEmClienteProduto(ClienteSacolaDTO produto) {
        List<Produto> produtos = produtoRepository
                .findAllById(produto.getClienteProdutoDTO().stream().mapToLong(ClienteProduto::getId).boxed()
                        .toList());
        List<ClienteProduto> sortedClienteProdutos = produto.getClienteProdutoDTO().stream()
                .sorted(Comparator.comparingLong(ClienteProduto::getId))
                .collect(Collectors.toList());
        List<ClienteProduto> clienteProdutos = new ArrayList<>();
        for (int i = 0; i < produtos.size(); i++) {
            Produto produtoExistente = produtos.get(i);
            if (produtoExistente != null) {
                ClienteProduto p1 = new ClienteProduto();
                p1.setProduto(produtoExistente);
                p1.setQuantidadeProduto(sortedClienteProdutos.get(i).getQuantidadeProduto());
                p1.setValorTotalDeProduto(produtos.get(i));
                clienteProdutos.add(p1);
                clienteProdutoRepository.save(p1);
            }
        }
        return clienteProdutos;
    }
}
