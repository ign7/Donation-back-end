package com.backendduation.demo.enums;

public enum DonationRole {
	
	DISPONIVEL("DISPONIVEL"),
	ENCERRADA("ENCERRADA"),
	INDISPONIVEL("RECUSADA");
	
	 private String role;
	
	

	private DonationRole(String role) {
		this.role = role;
	}



	public String getRole() {
		return role;
	}
	

}
