package com.api.films.controller;

import com.api.films.model.Review;
import com.api.films.repository.ReviewRepository;
import com.api.films.repository.UsuarioRepository;
import com.api.films.model.Usuario;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final UsuarioRepository usuarioRepository;

    public ReviewController(ReviewRepository reviewRepository, UsuarioRepository usuarioRepository) {
        this.reviewRepository = reviewRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/filme/{filmeId}")
    public List<Review> listarPorFilme(@PathVariable Integer filmeId) {
        List<Review> reviews = reviewRepository.findByFilmeId(filmeId);

        // Adiciona o nome do usuário a cada review para exibir no frontend
        reviews.forEach(review -> {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(review.getUsuarioId());
            usuarioOpt.ifPresent(usuario -> review.setNomeUsuario(usuario.getNome()));
        });

        return reviews;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Review review, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body("Precisa estar autenticado para postar review");

        String email = authentication.getName();
        var opt = usuarioRepository.findByEmail(email);

        if (opt.isEmpty()) return ResponseEntity.status(401).body("Usuário inválido");
        Usuario usuario = opt.get();

        review.setUsuarioId(usuario.getId());
        review.setNomeUsuario(usuario.getNome());

        // Garante que a data de criação seja gerada (caso sua entidade não faça isso automaticamente)
        // Se sua entidade Review usa @PrePersist, isso não é necessário, mas não faz mal garantir
        if (review.getCriadoEm() == null) {
            review.setCriadoEm(java.time.LocalDateTime.now());
        }

        Review salvo = reviewRepository.save(review);
        return ResponseEntity.created(URI.create("/reviews/" + salvo.getId())).body(salvo);
    }

    // --- NOVO MÉTODO ADICIONADO ---
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Review novosDados, Authentication authentication) {
        // 1. Verificação de Autenticação
        if (authentication == null) return ResponseEntity.status(401).build();

        String email = authentication.getName();
        var usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) return ResponseEntity.status(401).build();
        Usuario usuario = usuarioOpt.get();

        // 2. Busca a review existente no banco
        Optional<Review> reviewExistenteOpt = reviewRepository.findById(id);

        if (reviewExistenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review reviewExistente = reviewExistenteOpt.get();

        // 3. Segurança: Verifica se a review pertence ao usuário logado
        if (!reviewExistente.getUsuarioId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body("Você só pode editar suas próprias reviews");
        }

        // 4. Atualiza APENAS os campos permitidos (texto e nota)
        // Não atualizamos filmeId, usuarioId ou id da review
        reviewExistente.setTexto(novosDados.getTexto());
        reviewExistente.setNota(novosDados.getNota());

        // 5. Salva e retorna
        Review reviewAtualizada = reviewRepository.save(reviewExistente);

        // Garante que o nome do usuário volte na resposta
        reviewAtualizada.setNomeUsuario(usuario.getNome());

        return ResponseEntity.ok(reviewAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();

        String email = authentication.getName();
        var usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) return ResponseEntity.status(401).build();
        Usuario usuario = usuarioOpt.get();

        Optional<Review> reviewOpt = reviewRepository.findById(id);

        if (reviewOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review review = reviewOpt.get();

        // Verifica se o usuário é dono da review (segurança)
        if (!review.getUsuarioId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body("Você só pode apagar suas próprias reviews");
        }

        reviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}