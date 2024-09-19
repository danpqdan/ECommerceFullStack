package com.apiecommerce.apiecomerce.server.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    Double preco;
    int quantidadeEmSacola;
    int quantidadeEmEstoque;
    String descricao;
    Double valorTotal;
    @OneToOne
    Imagens imagem;

    public int setQuantidadeParaSacola(int quantidade) {
        this.quantidadeEmSacola = this.quantidadeEmSacola * 0;
        this.quantidadeEmSacola = +quantidade;
        return quantidadeEmSacola;
    }

    public void setQuantidadeEmSacola(int quantidadeEmSacola) {
        this.quantidadeEmSacola = this.quantidadeEmSacola * 0;
        this.quantidadeEmSacola = quantidadeEmSacola;
    }

    public void setValorTotal(Double preco, int quantidade) {
        this.valorTotal = preco * quantidade;
    }

}
