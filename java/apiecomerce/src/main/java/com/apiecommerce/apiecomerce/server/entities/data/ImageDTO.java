package com.apiecommerce.apiecomerce.server.entities.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    String nome;
    String descricao;
    String urlPrincipal;
    String urlMiniatura;
    String urlMiniatura2;
    String urlMiniatura3;
    String urlMiniatura4;
}
