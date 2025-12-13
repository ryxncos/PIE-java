package com.api.films.service;

import com.api.films.model.Usuario;
import com.api.films.model.Role;
import com.api.films.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(String nome, String email, String senha) {
        String encoded = passwordEncoder.encode(senha);
        Usuario u = new Usuario(nome, email, encoded, Role.ROLE_USER);
        return usuarioRepository.save(u);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
