package com.apiecommerce.apiecomerce.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.client.services.ClienteSacolaService;

@RequestMapping("/cliente")
@RestController
public class ClienteController {

    @Autowired
    ClienteSacolaService clienteSacolaService;

    @GetMapping
    public ResponseEntity retornarSacola(@RequestBody SacolaDTO dto) {
        return ResponseEntity.ok().body(clienteSacolaService.retornarSacola(dto.getLogin()));
    }

    @PostMapping("/sacola")
    public ResponseEntity clienteSacolaId(@RequestBody ClienteSacolaDTO sacolaDTO) {
        var sacola = clienteSacolaService.prepararSacolaCliente(sacolaDTO);
        return ResponseEntity.ok().body(sacola);
    }

    @PostMapping("/sacola/finalizar")
    public ResponseEntity sacolaPaga(@RequestBody SacolaDTO sacola) {
        return ResponseEntity.ok().body(clienteSacolaService.finalizarSacola(sacola));
    }

}
