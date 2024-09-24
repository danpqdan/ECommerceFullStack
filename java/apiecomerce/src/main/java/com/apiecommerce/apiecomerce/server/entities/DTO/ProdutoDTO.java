package com.apiecommerce.apiecomerce.server.entities.DTO;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.entities.Produto;
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
    int quantidadeEmEstoque;
    int quantidadeEmSacola;
    Double valorTotalEmEstoque;
    Double valorTotalEmSacola;
    Imagens imagens;

    public ProdutoDTO(Produto produtos) {
        this.id = produtos.getId();
        this.nomeDoProduto = produtos.getNome();
        this.preco = produtos.getPreco();
        this.descricao = produtos.getDescricao();
        this.quantidadeEmSacola = produtos.getQuantidadeEmSacola();
        this.quantidadeEmEstoque = produtos.getQuantidadeEmEstoque();
    }

}
