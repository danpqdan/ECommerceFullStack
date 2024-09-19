package com.apiecommerce.apiecomerce.server.entities.DTO;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produtos;
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
    Double preco;
    String descricao;
    int quantidadeDisponivel;
    Double valorTotal;
    Imagens imagens;

    public ProdutoDTO(Produtos produtos) {
        this.id = produtos.getId();
        this.nomeDoProduto = produtos.getNome();
        this.preco = produtos.getPreco();
        this.descricao = produtos.getDescricao();
        this.quantidadeDisponivel = produtos.getQuantidadeEmSacola();
    }

}
