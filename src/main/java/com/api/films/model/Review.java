package com.api.films.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    @Transient
    private String nomeUsuario;
    private Integer filmeId;

    @Column(length = 2000)
    private String texto;
    private Integer nota;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public Review() {}
    public Review(Long usuarioId, Integer filmeId, String texto, Integer nota, String nomeUsuario) {
        this.usuarioId = usuarioId;
        this.filmeId = filmeId;
        this.texto = texto;
        this.nota = nota;
        this.nomeUsuario = nomeUsuario;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Integer getFilmeId() { return filmeId; }
    public void setFilmeId(Integer filmeId) { this.filmeId = filmeId; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    @Override
    public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Review)) return false; Review r = (Review) o; return Objects.equals(id, r.id); }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
