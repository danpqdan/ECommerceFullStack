package com.apiecommerce.apiecomerce.server.entities.DTO;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
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
public class ProdutoDTO {
    Long id;
    String nomeDoProduto;
    Float preco;
    String descricao;
    int quantidadeDisponivel;
    Imagens imagens;
}
