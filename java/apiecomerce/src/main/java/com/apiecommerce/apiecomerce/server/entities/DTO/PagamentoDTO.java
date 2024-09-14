package com.apiecommerce.apiecomerce.server.entities.DTO;

public record PagamentoDTO(
    String numeroCartao,
    String validadeCartao,
    String codCartao
) {
    
}
