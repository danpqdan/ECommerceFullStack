package com.apiecommerce.apiecomerce.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "produto_server")
@Table(name = "produto_server")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerProduto extends Produto {

    int quantidadeEmEstoque;
    Double valorTotalEmEstoque;

    ServerProduto(String nome, Double preco, String descricao) {
        super(nome, preco, descricao);
    }

    public ServerProduto(String nome, Double preco, String descricao, int quantidadeEmEstoque) {
        super(nome, preco, descricao);
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public void setValorTotalEmEstoque() {
        this.valorTotalEmEstoque = getPreco() * getQuantidadeEmEstoque();
    }

}
