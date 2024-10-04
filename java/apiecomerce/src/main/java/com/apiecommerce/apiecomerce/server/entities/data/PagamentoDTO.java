package com.apiecommerce.apiecomerce.server.entities.data;

public record PagamentoDTO(
    String numeroCartao,
    String validadeCartao,
    String codCartao
) {
    
}
