package com.apiecommerce.apiecomerce.client.entities.data;

import java.util.List;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ClienteSacolaDTO {
    @Transient
    @JsonProperty("login")
    AuthenticationDTO login;
    List<ClienteProduto> clienteProdutoDTO;
}
