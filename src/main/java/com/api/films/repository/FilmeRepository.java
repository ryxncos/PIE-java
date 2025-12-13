package com.api.films.repository;

import com.api.films.model.Filmes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filmes, Integer> {
}
