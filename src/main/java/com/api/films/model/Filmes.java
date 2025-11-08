package com.api.films.model;
//Pacote responsávael por criar a classe modelo do banco de dados e suas entidades

import javax.persistence.*;


@Entity(name = "filmes")
@Table(name = "filmes")
public class Filmes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Estratégia de valor do "int id" automatico
	private Integer id;
	private String titulo;
	private String diretor;
	private String genero;
	private String anoLancamento;
	private String sinopse;
	private String elenco;
	private String review;
	
	
	public Filmes(){
		
	}
	
	public Filmes(Integer id, String titulo, String diretor, String genero, String anoLancamento, String sinopse, String elenco, String review) {
		// this => é a palavra chave que é usada para se referir ao atual objeto, sendo ele o Filmes...
		this.id = id;
		this.titulo = titulo;
		this.diretor = diretor;
		this.genero = genero;
		this.anoLancamento = anoLancamento;
		this.sinopse = sinopse;
		this.elenco = elenco;
		this.review = review;
		
		
	}
	
	// get/set ID 
	public Integer getId(){
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	// get/set titulo
	public String getTitulo() {
		return titulo;		
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	// get/set diretor
	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}
	
	// get/set genero
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	// get/set anoLancamento
	public String getAnoLancamento() {
		return anoLancamento;
	}
	public void setAnoLancamento(String anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	
	// get/set sinopse
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	
	// get/set elenco
	public String getElenco() {
		return elenco;
	}
	public void setElenco(String elenco) {
		this.elenco = elenco;
	}
	
	// get/set review
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
