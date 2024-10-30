package com.apiecommerce.apiecomerce.client.entities;

import java.util.List;
import java.util.UUID;

import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "produto_cliente")
public class ClienteProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false) // Certifique-se de que não seja nulo
    private Produto produto;

    int quantidadeProduto;
    double valorTotalDeProduto;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    SacolaCliente sacola;

    public ClienteProduto() {
    }

    public void setValorTotalDeProduto(Produto produtos) {
        var valorFinal = 0.0;
        var preco = produtos.getPreco();
        valorFinal += preco * this.quantidadeProduto;
        this.valorTotalDeProduto = valorFinal;
    }

}
