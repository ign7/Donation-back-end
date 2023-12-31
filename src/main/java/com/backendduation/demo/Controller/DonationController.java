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
import com.backendduation.demo.Repository.DonationRepository;
import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.DonationService;
import com.backendduation.demo.enums.Categoria;
import com.backendduation.demo.enums.DonationRole;
import com.backendduation.demo.enums.UserRole;

@RestController
@RequestMapping("/donations")
public class DonationController {

	@Autowired
	DonationService service;

	@Autowired
	UserRepository repository;

	@Autowired
	DonationRepository drepo;

	UserRole role;

	@GetMapping("/todos")
	public ResponseEntity<List<Donation>> findAll() {
		List<Donation> itens = service.findAll();
		return ResponseEntity.ok().body(itens);
	}

	@GetMapping("/pesquisardoacao/nomedoacao={nome}")
	public ResponseEntity<List<Donation>> findByNome(@PathVariable String nome) {
		List<Donation> doacoes = drepo.findBynome(nome);
		return ResponseEntity.ok().body(doacoes);
	}

	@GetMapping("/pesquisardoacao/iduser={id}")
	public ResponseEntity<List<Donation>> findByUser(@PathVariable Long id) {
		User dono = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id nao encontrado de usuario"));
		List<Donation> doacoes = dono.getDoacoes();
		return ResponseEntity.ok().body(doacoes);
	}

	@GetMapping("/pesquisardoacao/categoriadoacao={categoria}")
	public ResponseEntity<List<Donation>> findByCategoria(@PathVariable Categoria categoria) {
		List<Donation> doacoes = drepo.findBycategoria(categoria);
		return ResponseEntity.ok().body(doacoes);
	}

	@GetMapping("/pesquisardoacao/getuserbynomeDonation={nomeDonation}")
	public ResponseEntity<User> findByDonoDonation(@PathVariable String nomeDonation) {
		List<Donation> donation = service.findBynome(nomeDonation);
		User donodaporratoda = new User();
		for (Donation donations : donation) {
			donodaporratoda = donations.getUsuario();
		}

		return ResponseEntity.ok().body(donodaporratoda);
	}

	@PostMapping("/cadastrardonation/{login}")
	public ResponseEntity<Donation> insert(@RequestBody @Validated Donation item, @PathVariable("login") String login) {
		User usuario = repository.findByLogin(login);
		item.setUsuario(usuario);
		usuario.getDoacoes().add(item);
		item.setStatus(DonationRole.DISPONIVEL);
		item = service.insert(item);
		return ResponseEntity.ok().body(item);
	}

}
