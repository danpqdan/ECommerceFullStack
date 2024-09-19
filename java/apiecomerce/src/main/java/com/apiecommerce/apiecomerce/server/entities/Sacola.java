package com.apiecommerce.apiecomerce.server.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.apiecommerce.apiecomerce.server.entities.DTO.ProdutoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sacola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sacola_produtos", joinColumns = @JoinColumn(name = "sacola_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    List<Produtos> produtos = new ArrayList<>();
    Double valorFinal;
    @Enumerated(EnumType.STRING)
    EstadoDaCompra estadoDaCompra;

    public double setValorTotalSacola(List<Produtos> produto) {
        List<Double> novoValorFinal = new ArrayList<>();
        for (Produtos p1 : produto) {
            var valor = p1.getPreco();
            var quantidade = p1.getQuantidadeEmSacola();
            p1.setQuantidadeParaSacola(quantidade);
            novoValorFinal.add(valor * quantidade);
        }
        this.valorFinal = novoValorFinal.stream().mapToDouble(Double::doubleValue).sum();
        return this.valorFinal;
    }

}
