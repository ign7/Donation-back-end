package com.backendduation.demo.Entity;

public enum Categoria {
	
	ELETRONICO("eletronico"),
	 MATERIALESCOLAR("materialescolar"),
	 LIVRO("livro"),
	 CADERNO("caderno");
	
	private String categoria;

	Categoria(String categoria) {
		this.categoria=categoria;
	}
	
	public String getCategoria() {
		return categoria;
	}

}
