package com.api.films.config;

// 1. IMPORTS CORRIGIDOS (Aponte para o pacote service)
import com.api.films.service.TokenService;
import com.api.films.service.AutenticacaoService;
import com.api.films.repository.UsuarioRepository; // Pode remover se não for usar direto, mas deixei por segurança

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);

        if (token != null) {
            String email = tokenService.validarToken(token);

            if (email != null && !email.isEmpty()) {
                // 2. LÓGICA OTIMIZADA

                try {
                    UserDetails usuario = autenticacaoService.loadUserByUsername(email);

                    if (usuario != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    // Se der erro ao buscar usuário (ex: usuário deletado mas token ainda válido),
                    // apenas ignoramos e o usuário segue como "não logado".
                    System.out.println("Erro ao definir autenticação: " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) return null;
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}