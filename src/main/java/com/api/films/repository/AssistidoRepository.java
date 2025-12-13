package com.api.films.repository;

import com.api.films.model.Assistido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssistidoRepository extends JpaRepository<Assistido, Long> {
    List<Assistido> findByUsuarioId(Long usuarioId);
    void deleteByFilmeId(Integer filmeId);
    void deleteByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndFilmeId(Long usuarioId, Integer filmeId);  // ADICIONE
    void deleteByUsuarioIdAndFilmeId(Long usuarioId, Integer filmeId);  // ADICIONE
}
