package com.api.films.config;

import com.api.films.model.Usuario;
import com.api.films.model.Role;
import com.api.films.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@admin.com";
            if (!usuarioRepository.existsByEmail(adminEmail)) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setEmail(adminEmail);
                admin.setSenha(passwordEncoder.encode("admin"));
                admin.setRole(Role.ROLE_ADMIN);
                usuarioRepository.save(admin);
                System.out.println("Admin criado: admin@admin.com / admin");
            }
        };
    }
}
