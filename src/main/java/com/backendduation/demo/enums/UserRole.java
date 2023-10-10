package com.backendduation.demo.enums;

public enum UserRole {
	
	DOADOR("DOADOR"),
	RECEPTOR("RECEPTOR");
	
	private String role;
	
	UserRole(String role){
		this.role=role;
	}
	
	public String GetRole(){
		return role;
	}

}
