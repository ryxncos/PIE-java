package com.api.films.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "assistidos")
public class Assistido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private Integer filmeId;

    public Assistido() {}
    public Assistido(Long usuarioId, Integer filmeId) { this.usuarioId = usuarioId; this.filmeId = filmeId; }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Integer getFilmeId() { return filmeId; }
    public void setFilmeId(Integer filmeId) { this.filmeId = filmeId; }

    @Override
    public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Assistido)) return false; Assistido a = (Assistido) o; return Objects.equals(id, a.id); }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
