package com.apiecommerce.apiecomerce.server.entities;

import java.util.ArrayList;
import java.util.List;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "sacola_server")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class SacolaServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Usuario usuario;

    Double valorFinal;

    @Enumerated(EnumType.STRING)
    EstadoDaCompra estadoDaCompra;

    public SacolaServer(Usuario usuario, EstadoDaCompra estadoDaCompra) {
        this.usuario = usuario;
        this.estadoDaCompra = estadoDaCompra;
    }

    public SacolaServer(Usuario usuario2) {
        this.usuario = usuario2;
    }

}
