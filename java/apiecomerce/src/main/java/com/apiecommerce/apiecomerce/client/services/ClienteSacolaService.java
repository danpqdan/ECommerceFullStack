package com.apiecommerce.apiecomerce.client.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteProdutoRepository;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.LoginResponseDTO;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;
import com.apiecommerce.apiecomerce.server.services.CustomUserDetailsService;
import com.apiecommerce.apiecomerce.server.services.SacolaService;

@Service
public class ClienteSacolaService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ClienteSacolaRepository clienteSacolaRepository;
    @Autowired
    ClienteProdutoService clienteProdutoService;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    ClienteProdutoRepository clienteProdutoRepository;
    @Autowired
    SacolaRepository sacolaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SacolaService sacolaService;

    public SacolaServer retornarSacola(LoginResponseDTO login) {
        Usuario usuario = userDetailsService.validarUsuario(login);
        SacolaServer sacola = sacolaRepository.findByUsuarioId(usuario.getId());
        return sacola;
    }

    public SacolaCliente transformaSacolaEmSacolaCliente(LoginResponseDTO login) {
        SacolaServer sacola = retornarSacola(login);
        SacolaCliente sacolaCliente = new SacolaCliente();
        sacolaCliente.setId(sacola.getId());
        sacolaCliente.setEstadoDaCompra(sacola.getEstadoDaCompra());
        sacolaCliente.setUsuario(sacola.getUsuario());
        List<ClienteProduto> produto = clienteProdutoRepository.encontreTodosOsClienteProdutoPorId(sacola.getId());
        sacolaCliente.setClienteProduto(produto);
        sacolaCliente.setValorFinal(produto);
        clienteSacolaRepository.save(sacolaCliente);
        return sacolaCliente;
    }

    public List<ClienteProduto> prepararSacolaCliente(ClienteSacolaDTO clienteProduto) {
        SacolaCliente sacola = transformaSacolaEmSacolaCliente(clienteProduto.getLogin());
        List<ClienteProduto> clienteProduto2 = clienteProdutoService
                .adicionandoProdutosClientePorProduto(clienteProduto);
        for (int i = 0; i < clienteProduto2.size(); i++) {
            clienteProduto2.get(i).setSacola(sacola);
        }
        sacola.getClienteProduto().addAll(clienteProduto2);
        sacola.setValorFinal(sacola.getClienteProduto());
        //sacolaService.valorFinal(clienteProduto);
        sacola.setValorFinal(clienteProduto2);
        clienteProdutoRepository.saveAllAndFlush(clienteProduto2);
        clienteSacolaRepository.saveAndFlush(sacola);
        return clienteProduto2;
    }

    public String finalizarSacola(LoginResponseDTO sacolaDTO) {
        SacolaServer sacola = retornarSacola(sacolaDTO);
        Usuario usuario = userDetailsService.validarUsuario(sacolaDTO);
        if (sacola.getEstadoDaCompra() != EstadoDaCompra.APROVADA) {
            return "Sacola ainda não finalizada";
        } else {
            clienteSacolaRepository.deleteById(sacola.getId());
            SacolaCliente sacolaServer = new SacolaCliente();
            sacolaServer.setUsuario(sacola.getUsuario());
            sacolaServer.setEstadoDaCompra(EstadoDaCompra.PENDENTE);
            sacolaServer.setValorFinal(0.0);
            clienteSacolaRepository.save(sacolaServer);
            // usuario
            usuario.setSacola(sacolaServer);
            usuarioRepository.save(usuario);
            return "Sacola finalizada";
        }
    }

}
