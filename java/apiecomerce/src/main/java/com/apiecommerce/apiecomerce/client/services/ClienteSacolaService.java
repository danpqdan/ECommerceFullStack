package com.apiecommerce.apiecomerce.client.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteProdutoRepository;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;
import com.apiecommerce.apiecomerce.server.services.CustomUserDetailsService;

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

    public SacolaServer retornarSacola(AuthenticationDTO login) {
        Usuario usuario = userDetailsService.validarUsuario(login);
        SacolaServer sacola = sacolaRepository.findByUsuarioId(usuario.getId());
        return sacola;
    }

    public SacolaCliente transformaSacolaEmSacolaCliente(AuthenticationDTO login) {
        SacolaServer sacola = retornarSacola(login);
        SacolaCliente sacolaCliente = new SacolaCliente();
        sacolaCliente.setId(sacola.getId());
        sacolaCliente.setEstadoDaCompra(sacola.getEstadoDaCompra());
        sacolaCliente.setUsuario(sacola.getUsuario());
        // clienteSacolaRepository.save(sacolaCliente);
        return sacolaCliente;
    }

    public List<ClienteProduto> prepararSacolaCliente(ClienteSacolaDTO clienteProduto) {
        SacolaCliente sacola = transformaSacolaEmSacolaCliente(clienteProduto.getLogin());
        List<ClienteProduto> clienteProduto2 = clienteProdutoService.retornarProdutoEmClienteProduto(clienteProduto);
        for (int i = 0; i < clienteProduto2.size(); i++) {
            clienteProduto2.get(i).setSacola(sacola);
        }
        sacola.setClienteProduto(clienteProduto2);
        sacola.setValorFinal(clienteProduto2);
        clienteProdutoRepository.saveAll(clienteProduto2);
        clienteSacolaRepository.saveAndFlush(sacola);
        return clienteProduto2;
    }

    public String finalizarSacola(SacolaDTO sacolaDTO) {
        SacolaServer sacola = retornarSacola(sacolaDTO.getLogin());
        Usuario usuario = userDetailsService.validarUsuario(sacolaDTO.getLogin());
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
