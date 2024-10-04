package com.apiecommerce.apiecomerce.server.entities.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
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
    String descricao;
    Double preco;
    int quantidadeEmEstoque;
    Double valorTotalEmEstoque;

    public ProdutoDTO(long idProduto, double valorProduto) {
        this.id = idProduto;
        this.preco = valorProduto;
    }

}
