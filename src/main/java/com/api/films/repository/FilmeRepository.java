package com.api.films.repository;
import com.api.films.model.Filmes;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

//Para cada entidade mapeada, é necessário a criação de um 'repository' para ela...
//É nele que vai ser criado os métodos de CRUD

public interface FilmeRepository extends JpaRepository <Filmes, Integer> {

}
