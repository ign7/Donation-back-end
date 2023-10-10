package com.backendduation.demo.Entity;

import com.backendduation.demo.enums.UserRole;

public record RegisterDTO(String login,String password,String nome,String email,Integer idade,String telefone,UserRole role) {

}
