package com.apiecommerce.apiecomerce.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Imagens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String descricao;
    String urlPrincipal;
    String urlMiniatura;
    String urlMiniatura2;
    String urlMiniatura3;
    String urlMiniatura4;

    public Imagens(String nome, String descricao, byte[] dados) {
        this.nome = nome;
        this.descricao = descricao;
    }

}
