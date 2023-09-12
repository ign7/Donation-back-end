package com.backendduation.demo.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping
	public List<User> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value="/{iduser}")	
	public ResponseEntity<Optional<User>> findbyid(@PathVariable Long iduser) {
	Optional<User> obj=service.findbyid(iduser);		
	return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj){
		obj=service.insert(obj);		
		URI uri =ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj); 
	}

}
