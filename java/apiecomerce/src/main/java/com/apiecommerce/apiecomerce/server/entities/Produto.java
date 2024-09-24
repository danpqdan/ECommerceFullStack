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
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    Double preco;
    int quantidadeEmSacola;
    int quantidadeEmEstoque;
    String descricao;
    Double valorTotalEmEstoque;
    Double valorTotalEmSacola;
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

    public void setQuantidadeEmEstoqueAtualizada(int quantidadeEmEstoque) {
        var emSacola = getQuantidadeEmSacola();
        this.quantidadeEmEstoque = quantidadeEmEstoque - emSacola;
    }

    public void setValorTotalEmEstoque(int quantidade, Double preco) {
        this.valorTotalEmEstoque = preco * quantidade;
    }

    public void setValorTotalEmSacola(int quantidade, Double preco) {
        this.valorTotalEmSacola = preco * quantidade;
    }

    @Override
    public String toString() {
        return "Produtos [id=" + id + ", nome=" + nome + ", preco=" + preco + ", descricao=" + descricao + "]";
    }

}
