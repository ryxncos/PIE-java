package com.api.films.dto;

public class FilmeResponseDTO {
    private Integer id;
    private String titulo;
    private String diretor;
    private String genero;
    private String anoLancamento;
    private String sinopse;
    private String elenco;
    private String imagem; // URL da imagem ao invés de byte[]

    public FilmeResponseDTO() {}

    public FilmeResponseDTO(Integer id, String titulo, String diretor, String genero,
                            String anoLancamento, String sinopse, String elenco, String imagem) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.elenco = elenco;
        this.imagem = imagem;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}