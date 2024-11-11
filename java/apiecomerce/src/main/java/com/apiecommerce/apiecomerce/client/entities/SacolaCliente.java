package com.apiecommerce.apiecomerce.client.entities;

import java.util.ArrayList;
import java.util.List;

import com.apiecommerce.apiecomerce.server.entities.EstadoDaCompra;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.apiecommerce.apiecomerce.server.entities.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "sacola_cliente")
@Entity(name = "sacola_cliente")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SacolaCliente extends SacolaServer {

    Long paymentId;
    String preferenceId;
    String status;
    String status_detail;
    Double transaction_amount;
    String payment_method_id;
    String payment_type_id;
    String date_approved;
    Double valorFinalSacola;

    // @JsonBackReference
    @OneToMany(mappedBy = "sacola", cascade = CascadeType.MERGE, orphanRemoval = true)
    List<ClienteProduto> clienteProduto = new ArrayList<>();

    public SacolaCliente(Long id, Usuario usuario, Double valorFinal, EstadoDaCompra estadoDaCompra) {
        super(id, usuario, valorFinal, estadoDaCompra);
        super.setValorFinal(this.getValorFinal());
    }

    public SacolaCliente(Usuario usuario) {
        super(usuario);
    }

    public SacolaCliente(SacolaServer sacola) {
    }

    public void setValorFinal(List<ClienteProduto> clienteProdutos) {
        double valorPorProduto = 0.0;
        for (ClienteProduto clienteProduto : clienteProdutos) {
            valorPorProduto += clienteProduto.getValorTotalDeProduto();
        }
        this.valorFinalSacola = valorPorProduto;
        this.transaction_amount = valorPorProduto;
    }

}
