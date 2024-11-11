package com.apiecommerce.apiecomerce.server.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "produto")
@Table(name = "produto")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    Double preco;
    String descricao;
    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    Categoria categoria;
    @OneToOne
    Imagens imagem;

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Produto(Imagens imagens) {
        this.imagem = imagens;
    }

    public Produto(String nome, Double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Produto(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produtos [id=" + id + ", nome=" + nome + ", preco=" + preco + ", descricao=" + descricao + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
