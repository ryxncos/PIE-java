package com.api.films; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class PieApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PieApplication.class, args);
        System.out.println("Aplicação rodando na porta 8080");
    }
}