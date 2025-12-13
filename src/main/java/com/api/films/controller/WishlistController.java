package com.api.films.controller;

import com.api.films.model.Wishlist;
import com.api.films.repository.UsuarioRepository;
import com.api.films.repository.WishlistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional; // Importante para o Delete
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final UsuarioRepository usuarioRepository;

    public WishlistController(WishlistRepository wishlistRepository, UsuarioRepository usuarioRepository) {
        this.wishlistRepository = wishlistRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/{filmeId}")

    public ResponseEntity<?> adicionar(@PathVariable Integer filmeId, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Precisa autenticar");

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário inválido");

        Long usuarioId = opt.get().getId();

        // Evita duplicidade
        if (wishlistRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
            return ResponseEntity.badRequest().body("Já está na wishlist");
        }

        Wishlist w = new Wishlist(usuarioId, filmeId);
        Wishlist salvo = wishlistRepository.save(w);
        return ResponseEntity.created(URI.create("/wishlist/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public ResponseEntity<?> listar(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Precisa autenticar");

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário inválido");

        Long usuarioId = opt.get().getId();
        List<Wishlist> lista = wishlistRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(lista);
    }

    // --- NOVO MÉTODO PARA REMOVER ---
    @DeleteMapping("/{filmeId}")
    @Transactional // Necessário para operações de DELETE
    public ResponseEntity<?> remover(@PathVariable Integer filmeId, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).build();

        Long usuarioId = opt.get().getId();

        // Verifica se existe antes de tentar deletar
        if (wishlistRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
            wishlistRepository.deleteByUsuarioIdAndFilmeId(usuarioId, filmeId);
        }

        return ResponseEntity.noContent().build();
    }
}