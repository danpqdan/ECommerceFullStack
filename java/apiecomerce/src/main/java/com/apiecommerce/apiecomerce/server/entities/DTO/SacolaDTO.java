package com.apiecommerce.apiecomerce.server.entities.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SacolaDTO {
    Long sacolaID;
    List<ProdutoDTO> produtos = new ArrayList<>();
    Long usuarioID;
    double valorTotalSacola;

}
