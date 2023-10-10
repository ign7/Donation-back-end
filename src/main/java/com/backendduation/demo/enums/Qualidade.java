package com.backendduation.demo.enums;

public enum Qualidade {
	
	PERFEITO("PERFEITO"),
	BOMESTADO("BOM ESTADO"),
	USADO("USADO"),
	RUIM("RUIM");
	
	private String qualidade;
	
	 Qualidade(String qualidade) {
		this.qualidade=qualidade;
	}

	public String getQualidade() {
		return qualidade;
	}


	
	

}
