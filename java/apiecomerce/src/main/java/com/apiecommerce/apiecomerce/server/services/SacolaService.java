package com.apiecommerce.apiecomerce.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.ProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;

@Service
public class SacolaService {

    @Autowired
    SacolaRepository sacolaRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    CustomUserDetailsService userDetailsService;
   
}
