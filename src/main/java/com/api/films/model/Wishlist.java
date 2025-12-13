package com.api.films.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private Integer filmeId;

    public Wishlist() {}
    public Wishlist(Long usuarioId, Integer filmeId) { this.usuarioId = usuarioId; this.filmeId = filmeId; }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Integer getFilmeId() { return filmeId; }
    public void setFilmeId(Integer filmeId) { this.filmeId = filmeId; }

    @Override
    public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Wishlist)) return false; Wishlist w = (Wishlist) o; return Objects.equals(id, w.id); }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
