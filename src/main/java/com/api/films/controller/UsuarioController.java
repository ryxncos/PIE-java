package com.api.films.controller;

import com.api.films.model.Usuario;
import com.api.films.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Não autenticado");
        String email = authentication.getName();
        var opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Usuário não encontrado");
        Usuario u = opt.get();
        // Não retornar senha
        var dto = new Object() {
            public Long id = u.getId();
            public String nome = u.getNome();
            public String email = u.getEmail();
            public String role = u.getRole() == null ? "ROLE_USER" : u.getRole().name();
        };
        return ResponseEntity.ok(dto);
    }
}
