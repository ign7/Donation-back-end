package com.backendduation.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendduation.demo.Entity.Donation;
import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.DonationService;
import com.backendduation.demo.enums.DonationRole;
import com.backendduation.demo.enums.UserRole;


@RestController
@RequestMapping("/donations")
public class DonationController {
	
	@Autowired
	DonationService service;
	
	@Autowired
	UserRepository repository;
	
	UserRole role;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Donation>> findAll(){
		List<Donation> itens = service.findAll();
		return ResponseEntity.ok().body(itens);
	}
	
	@PostMapping("/cadastrardonation/{usuario_id}")
	public ResponseEntity<Donation> insert(@RequestBody @Validated Donation item,@PathVariable("usuario_id") Long id) {
		User usuario=repository.findById(id).orElseThrow(()->new IllegalArgumentException("Id nao encontrado"));			    
		item.setUsuario(usuario);
		usuario.getDoacoes().add(item);
		item.setStatus(DonationRole.DISPONIVEL);
	    item=service.insert(item);
		return ResponseEntity.ok().body(item);
	}
	
	
	//@PostMapping("/cadastrardonation/{usuario_id}")
	//public ResponseEntity<Donation> insert(@RequestBody Donation item,@PathVariable("usuario_id") Long id) {
		//User usuario=repository.findById(id).orElseThrow(()->new IllegalArgumentException("Id nao encontrado"));
		//if(usuario.getRole()==role.DOADOR) {
			//item.setUsuario(usuario);
			//usuario.getDoacoes().add(item);
		    //tem=service.insert(item);
			//return ResponseEntity.ok().body(item);
		//}
		//return ResponseEntity.badRequest().build();
		
	//}

}
