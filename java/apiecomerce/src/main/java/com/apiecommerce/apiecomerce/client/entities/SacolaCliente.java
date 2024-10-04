package com.apiecommerce.apiecomerce.client.entities;

import java.util.ArrayList;
import java.util.List;

import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.apiecommerce.apiecomerce.server.entities.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Setter;

@Entity(name = "sacola_cliente")
@Setter
public class SacolaCliente extends SacolaServer {

    double valorFinal;

    @OneToMany(mappedBy = "sacola", cascade = CascadeType.MERGE, orphanRemoval = true)
    List<ClienteProduto> clienteProduto = new ArrayList<>();

    public SacolaCliente(Long id, Usuario usuario, Double valorFinal, EstadoDaCompra estadoDaCompra) {
        super(id, usuario, valorFinal, estadoDaCompra);
    }

    public SacolaCliente(Usuario usuario) {
        super(usuario);
    }

    public SacolaCliente() {
    }

    public SacolaCliente(SacolaServer sacola) {
    }

    public void setValorFinal(List<ClienteProduto> clienteProdutos) {
        double valorPorProduto = 0.0;
        for (ClienteProduto clienteProduto : clienteProdutos) {
            valorPorProduto += clienteProduto.getValorTotalDeProduto();
        }
        this.valorFinal = valorPorProduto;
    }

}
