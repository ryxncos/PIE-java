package com.api.films.config;

import com.api.films.model.Usuario;
import com.api.films.model.Role;
import com.api.films.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner; // Importante: Estava faltando este import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {

    @Bean
    public CommandLineRunner criarAdmin(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {

            String adminEmail = "admin@admin.com";

            // Verifica se o admin já existe pelo e-mail
            if (repository.findByEmail(adminEmail).isEmpty()) {

                Usuario adm = new Usuario();
                adm.setNome("Administrador");
                adm.setEmail(adminEmail);

                // Criptografa a senha antes de salvar
                adm.setSenha(passwordEncoder.encode("123456"));


                adm.setRole(Role.ROLE_ADMIN);

                repository.save(adm);
                System.out.println("ADMINISTRADOR CRIADO COM SUCESSO!");
            } else {
                System.out.println("ADMINISTRADOR JÁ EXISTE.");
            }
        };
    }
}