package com.backendduation.demo.Entity;

public enum SolicitacaoRole {
	
	ACEITA("ACEITA"),
	AGUARDANDO("AGUARDANDO"),
	RECUSADA("RECUSADA");
	
	 private String role;
	
	

	private SolicitacaoRole(String role) {
		this.role = role;
	}



	public String getRole() {
		return role;
	}

	
	
	
	

}
