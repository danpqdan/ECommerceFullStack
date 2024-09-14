package com.apiecommerce.apiecomerce.server.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.services.ImagensService;

@RestController
@RequestMapping("/api/imagens")
public class ImagensController {

    @Autowired
    private ImagensService imagensService;

    // Endpoint para upload de arquivo
    @PostMapping("/upload")
    public ResponseEntity<String> uploadArquivo(
            @RequestParam("descricao") String descricao,
            @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            Imagens novaImagem = imagensService.salvarImagem(arquivo, descricao);
            return ResponseEntity.status(HttpStatus.OK).body("Arquivo salvo com sucesso! ID: " + novaImagem.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar o arquivo.");
        }
    }

    // Endpoint para baixar arquivo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadArquivo(@PathVariable Long id) {
        Optional<Imagens> arquivoOptional = imagensService.obterArquivoPorId(id);

        if (arquivoOptional.isPresent()) {
            Imagens imagem = arquivoOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagem.getNome() + "\"")
                    .body(imagem.getDados());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Imagens>> listarImagens() {
        List<Imagens> imagens = imagensService.listarImagens();

        return ResponseEntity.ok(imagens);
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id) {
        var img = imagensService.deleteImage(id);
        return "ok";
    }

    @GetMapping("/carouselhome")
    public ResponseEntity<List<Imagens>> imagensCarouselHome() {
        List<Imagens> imagens = imagensService.listarImagensCarousel();
        return ResponseEntity.ok(imagens);
    }
}
