package com.api.films.service;

import com.api.films.model.Usuario; // <--- Importante
import com.api.films.repository.UsuarioRepository; // <--- Importante
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final String SECRET = "troque-esta-chave-por-uma-muito-maior-e-segura-2025-KEY";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long expiration = 1000L * 60 * 60 * 24 * 7; // 7 dias

    // Injetamos o repositório para buscar o ID do usuário
    private final UsuarioRepository usuarioRepository;

    public TokenService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String gerarToken(Authentication authentication) {
        String email = authentication.getName(); // Pega o email do login
        Date agora = new Date();
        Date exp = new Date(agora.getTime() + expiration);

        // 1. Busca a Role
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        // 2. Busca o Usuário no banco para pegar o ID correto
        // Isso garante que temos o ID mesmo se o Principal for genérico
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Long usuarioId = usuarioOpt.map(Usuario::getId).orElse(0L);
        String nome = usuarioOpt.map(Usuario::getNome).orElse(email);

        // 3. Cria o Token com o ID dentro
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("id", usuarioId)
                .claim("nome", nome)
                .setIssuedAt(agora)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return "";
        }
    }
}