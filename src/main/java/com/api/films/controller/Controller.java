package com.api.films.controller;  

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmes")
public class Controller {  
    
    @GetMapping("/teste")
    public String testRoute() {
        return "Minha primeira rota está funcionando!";
    }
    
    
    
}