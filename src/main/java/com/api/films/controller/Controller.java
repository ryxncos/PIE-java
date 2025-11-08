package com.api.films.controller; 

import java.util.List;
import com.api.films.model.Filmes;
import com.api.films.repository.FilmeRepository;
import com.api.films.dtos.FilmesDto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Informando para a IDE que toda essa classe, se trata de uma classe de controle
@RequestMapping("/filmes")
public class Controller {  
	
    @Autowired
	FilmeRepository repository; //Instancia (é um objeto concreto criado a partir de uma classe) referenciando nosso repository
	
    @SuppressWarnings("rawtypes")
	@GetMapping("/api/filmes")
    public ResponseEntity<List> getAll() { //Response => Classe que contem métodos com as melhores práticas para o retorno de informações de uma API
        List<Filmes> listaFilmes = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listaFilmes);
        
    }
    
    @PostMapping("/post")
    public ResponseEntity<Filmes> save(@RequestBody FilmesDto dto) {
    	var filmes = new Filmes();
    	BeanUtils.copyProperties(dto, filmes);
    	return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(filmes));
    	
    	
    }
    
    
    
}