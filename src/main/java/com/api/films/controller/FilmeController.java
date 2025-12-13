package com.api.films.controller;

import com.api.films.model.Filmes;
import com.api.films.repository.FilmeRepository;
import com.api.films.service.FilmeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeRepository filmeRepository;
    private final FilmeService filmeService;

    public FilmeController(FilmeRepository filmeRepository, FilmeService filmeService) {
        this.filmeRepository = filmeRepository;
        this.filmeService = filmeService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Filmes> listarTodos() {
        List<Filmes> filmes = filmeRepository.findAll();
        // Adiciona a URL da imagem em cada filme
        return filmes.stream().map(filme -> {
            if (filme.getImagemData() != null && filme.getImagemData().length > 0) {
                filme.setImagem("/filmes/" + filme.getId() + "/imagem");
            }
            return filme;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Filmes> obter(@PathVariable Integer id) {
        return filmeRepository.findById(id)
                .map(filme -> {
                    if (filme.getImagemData() != null && filme.getImagemData().length > 0) {
                        filme.setImagem("/filmes/" + filme.getId() + "/imagem");
                    }
                    return ResponseEntity.ok(filme);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para servir a imagem como arquivo
    @GetMapping("/{id}/imagem")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> obterImagem(@PathVariable Integer id) {
        return filmeRepository.findById(id)
                .map(filme -> {
                    if (filme.getImagemData() != null && filme.getImagemData().length > 0) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG);
                        return new ResponseEntity<byte[]>(filme.getImagemData(), headers, HttpStatus.OK);
                    }
                    return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
                })
                .orElse(new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND));
    }

    // --- CRIAÇÃO DE FILME (POST) ---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Transactional
    public ResponseEntity<Filmes> criar(
            @RequestPart("filme") String filmeJson,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Filmes filme = mapper.readValue(filmeJson, Filmes.class);

        if (imagem != null && !imagem.isEmpty()) {
            filme.setImagemData(imagem.getBytes());
        }

        Filmes salvo = filmeRepository.save(filme);

        if (salvo.getImagemData() != null && salvo.getImagemData().length > 0) {
            salvo.setImagem("/filmes/" + salvo.getId() + "/imagem");
        }

        return ResponseEntity
                .created(URI.create("/filmes/" + salvo.getId()))
                .body(salvo);
    }

    // --- ATUALIZAÇÃO DE FILME (PUT) ---
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Transactional
    public ResponseEntity<Filmes> atualizar(
            @PathVariable Integer id,  // MUDOU: Long -> Integer
            @RequestPart("filme") String filmeJson,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        return filmeRepository.findById(id).map(existente -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Filmes filmeAtualizado = mapper.readValue(filmeJson, Filmes.class);

                existente.setTitulo(filmeAtualizado.getTitulo());
                existente.setDiretor(filmeAtualizado.getDiretor());
                existente.setGenero(filmeAtualizado.getGenero());
                existente.setSinopse(filmeAtualizado.getSinopse());
                existente.setAnoLancamento(filmeAtualizado.getAnoLancamento());
                existente.setElenco(filmeAtualizado.getElenco());

                if (imagem != null && !imagem.isEmpty()) {
                    existente.setImagemData(imagem.getBytes());
                }

                Filmes salvo = filmeRepository.save(existente);

                if (salvo.getImagemData() != null && salvo.getImagemData().length > 0) {
                    salvo.setImagem("/filmes/" + salvo.getId() + "/imagem");
                }

                return ResponseEntity.ok(salvo);
            } catch (IOException e) {
                return new ResponseEntity<Filmes>(HttpStatus.BAD_REQUEST);
            }
        }).orElse(new ResponseEntity<Filmes>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {  // MUDOU: Long -> Integer
        boolean sucesso = filmeService.deletarFilme(id);

        if (sucesso) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
