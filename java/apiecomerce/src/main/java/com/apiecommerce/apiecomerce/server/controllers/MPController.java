package com.apiecommerce.apiecomerce.server.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaProdutoDTO;
import com.apiecommerce.apiecomerce.server.security.MercadoPago;

@RequestMapping("/api")
@RestController
public class MPController {

    @Autowired
    MercadoPago mercadoPago;

    @Value("${token.mercadopago}")
    String token;

    @GetMapping("/produto")
    public ResponseEntity<Map<String, Object>> listarProdutos(@RequestBody SacolaProdutoDTO dto) {

        Map<String, Object> response = new HashMap<>();
        var lista = mercadoPago.listProduto(dto);
        var valor = mercadoPago.valorSacola(dto);

        response.put("Produtos", lista);
        response.put("Total", valor);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mp/sacola")
    public ResponseEntity postProduto(@RequestBody SacolaDTO dto) {
        return ResponseEntity.ok().body(mercadoPago.getPreferenceId(dto));
    }


    @GetMapping()
    public ModelAndView teste() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("checkout.html");
        return mv;
    }

}
