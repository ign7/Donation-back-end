package com.backendduation.demo.enums;

import jakarta.persistence.Entity;


public enum Categoria {
	
	ELETRONICO("eletronico"),
	MATERIALESCOLAR("materialescolar"),
	LIVRO("livro"),
	CADERNO("caderno"),
	UNIFORME("uniforme"),
	MOCHILA("mochila");
	
	private String categoria;
	

	Categoria(String categoria) {
		this.categoria=categoria;
	}
	
	public String getCategoria() {
		return categoria;
	}

}
