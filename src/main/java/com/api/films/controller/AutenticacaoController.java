package com.api.films.controller;

import com.api.films.service.TokenService;
import com.api.films.dtos.LoginDTO;
import com.api.films.dtos.RegistroDTO;
import com.api.films.dtos.TokenDTO;
import com.api.films.model.Usuario;
import com.api.films.repository.UsuarioRepository;
import com.api.films.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoController(AuthenticationManager authenticationManager,
                                  TokenService tokenService,
                                  UsuarioService usuarioService,
                                  UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        if (usuarioService.emailExiste(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        Usuario u = usuarioService.registrarUsuario(dto.getNome(), dto.getEmail(), dto.getSenha());
        return ResponseEntity.created(URI.create("/usuarios/" + u.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        UsernamePasswordAuthenticationToken dadosLogin =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}
