// src/main/java/com/api/films/dtos/FilmesDto.java

package com.api.films.dtos;


public record FilmesDto(
        String titulo,
        String diretor,
        String genero,
        String anoLancamento,
        String sinopse,
        String elenco,
        String review
) {

}