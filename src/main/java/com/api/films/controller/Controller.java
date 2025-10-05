package com.api.films.controller;  

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
public class Controller {  
    
    @GetMapping("/test")
    public String testRoute() {
        return "✅ Minha primeira rota está funcionando! 🎉";
    }
    
    
    
}