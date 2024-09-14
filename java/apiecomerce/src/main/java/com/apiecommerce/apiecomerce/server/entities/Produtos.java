package com.apiecommerce.apiecomerce.server.entities;

import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    Float preco;
    int quantidade;
    String descricao;
    @OneToOne
    Imagens imagem;

    public int somaQuantidade(int quantidade) {
        return this.quantidade += quantidade;
    }

    public double somaQuantidadeXPreco(int quantidade) {
        this.quantidade = +quantidade;
        return this.preco * quantidade;
    }

    public Produtos(int quantidade) {
        this.quantidade = quantidade + 1;
    }

}
