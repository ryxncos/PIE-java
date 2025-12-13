package com.api.films.repository;

import com.api.films.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUsuarioId(Long usuarioId);
    void deleteByFilmeId(Integer filmeId);
    void deleteByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndFilmeId(Long usuarioId, Integer filmeId);
    void deleteByUsuarioIdAndFilmeId(Long usuarioId, Integer filmeId);
}