package com.apiecommerce.apiecomerce.server.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apiecommerce.apiecomerce.server.entities.Imagens;
import com.apiecommerce.apiecomerce.server.interfaces.ImagensRepository;

@Service
public class ImagensService {

    @Autowired
    ImagensRepository imagensRepository;

    // Método para salvar o arquivo
    public Imagens salvarImagem(MultipartFile imagem, String descricao) throws IOException {
        String nomeImagem = imagem.getOriginalFilename();
        String descricaoImagem = descricao;
        byte[] dadosImagem = imagem.getBytes();

        Imagens novoImagem = new Imagens(nomeImagem, descricaoImagem, dadosImagem);
        return imagensRepository.save(novoImagem);
    }

    // Método para buscar o arquivo por ID
    public Optional<Imagens> obterArquivoPorId(Long id) {
        return imagensRepository.findById(id);
    }

    public List<Imagens> listarImagens() {
        return imagensRepository.findAll();
    }

    public List<Imagens> listarImagensCarousel() {
        return imagensRepository.findByDescricao("carouselhome");
    }

    public String deleteImage(Long id) {
        imagensRepository.deleteById(id);
        return "ok";
    }

}
