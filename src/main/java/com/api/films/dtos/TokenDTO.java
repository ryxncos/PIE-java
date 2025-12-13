package com.api.films.dtos;

public class TokenDTO {
    private String token;
    private String tipo = "Bearer";

    public TokenDTO(String token) { this.token = token; }
    public String getToken() { return token; }
    public String getTipo() { return tipo; }
}
