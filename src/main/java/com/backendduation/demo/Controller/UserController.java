package com.backendduation.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendduation.demo.Entity.LoginResponseDTO;
import com.backendduation.demo.Entity.RegisterDTO;
import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Entity.UserDTO;
import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.TokenService;
import com.backendduation.demo.Service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	TokenService Tokenservice;
	
	@Autowired
	private AuthenticationManager authetication;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public List<User> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value="id=/{iduser}")	
	public ResponseEntity<Optional<User>> findbyid(@PathVariable Long iduser) {
	Optional<User> obj=service.findbyid(iduser);		
	return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("/register")
	public ResponseEntity insert(@RequestBody @Validated RegisterDTO data){
		if(this.repository.findBylogin(data.login())!=null) return ResponseEntity.badRequest().build();		
		String criptografiadesenha= new BCryptPasswordEncoder().encode(data.password());
		User user = new User(data.login(),criptografiadesenha,data.nome(),data.email(),data.idade(),data.telefone(),data.role());
		this.repository.save(user);
		return ResponseEntity.ok().build();
		
	}
	
	
	
	@PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated UserDTO data) {
		var usernamepassword= new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth= this.authetication.authenticate(usernamepassword);
		var token=Tokenservice.generateToken((User)auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

}
