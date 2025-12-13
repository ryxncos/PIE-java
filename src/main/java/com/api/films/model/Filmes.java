// src/main/java/com/api/films/model/Filmes.java

package com.api.films.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity(name = "filmes")
@Table(name = "filmes")
public class Filmes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String diretor;
    private String genero;
    private String anoLancamento;
    private String sinopse;
    private String elenco;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "imagem_data")  // Usando nome diferente para evitar conflito
    @JsonIgnore  // Ignora este campo na serialização JSON
    private byte[] imagemData;

    @Transient  // Não persiste no banco, apenas para retorno na API
    private String imagem;

    public Filmes() {}

    public Filmes(Integer id, String titulo, String diretor, String genero, String anoLancamento, String sinopse, String elenco, byte[] imagemData) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.elenco = elenco;
        this.imagemData = imagemData;
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

    @JsonIgnore
    public byte[] getImagemData() {
        return imagemData;
    }

    public void setImagemData(byte[] imagemData) {
        this.imagemData = imagemData;
    }

    // Este campo é usado para retornar a URL da imagem na API
    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
