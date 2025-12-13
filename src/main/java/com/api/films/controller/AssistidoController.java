package com.api.films.controller;

import com.api.films.model.Assistido;
import com.api.films.repository.AssistidoRepository;
import com.api.films.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional; // Importante para o DELETE
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/assistidos")
public class AssistidoController {

    private final AssistidoRepository assistidoRepository;
    private final UsuarioRepository usuarioRepository;

    public AssistidoController(AssistidoRepository assistidoRepository, UsuarioRepository usuarioRepository) {
        this.assistidoRepository = assistidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/{filmeId}")
    public ResponseEntity<?> marcarAssistido(@PathVariable Integer filmeId, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Precisa autenticar");

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário inválido");

        Long usuarioId = opt.get().getId();

        // Verifica se já marcou para não duplicar
        if (assistidoRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
            return ResponseEntity.badRequest().body("Já marcado como assistido");
        }

        Assistido a = new Assistido(usuarioId, filmeId);
        Assistido salvo = assistidoRepository.save(a);
        return ResponseEntity.created(URI.create("/assistidos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public ResponseEntity<?> listar(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Precisa autenticar");

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário inválido");

        Long usuarioId = opt.get().getId();
        List<Assistido> lista = assistidoRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(lista);
    }

    // --- NOVO MÉTODO PARA DESMARCAR (REMOVER) ---
    @DeleteMapping("/{filmeId}")
    @Transactional // Obrigatório para operações de delete
    public ResponseEntity<?> desmarcar(@PathVariable Integer filmeId, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();

        var opt = usuarioRepository.findByEmail(authentication.getName());
        if (opt.isEmpty()) return ResponseEntity.status(401).build();

        Long usuarioId = opt.get().getId();

        // Verifica se existe antes de tentar deletar
        if (assistidoRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
            assistidoRepository.deleteByUsuarioIdAndFilmeId(usuarioId, filmeId);
        }

        return ResponseEntity.noContent().build();
    }
}