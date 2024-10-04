package com.apiecommerce.apiecomerce.client.entities.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.apiecommerce.apiecomerce.server.entities.Produto;
import com.apiecommerce.apiecomerce.server.interfaces.ProdutoRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SacolaDTO {
    AuthenticationDTO login;

    public AuthenticationDTO getLogin() {
        return login;
    }

    public void setLogin(AuthenticationDTO login) {
        this.login = login;
    }

}
