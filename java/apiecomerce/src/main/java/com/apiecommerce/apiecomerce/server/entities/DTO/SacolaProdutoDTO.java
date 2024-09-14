package com.apiecommerce.apiecomerce.server.entities.DTO;

import com.apiecommerce.apiecomerce.server.entities.Imagens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SacolaProdutoDTO {
    long sacolaID;
    long produtoID;
    long usuarioID;
    int quantidade;
    double valorTotalSacola;
}
