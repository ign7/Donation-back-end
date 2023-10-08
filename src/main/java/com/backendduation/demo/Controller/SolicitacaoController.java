package com.backendduation.demo.Controller;

import java.util.ArrayList;
import java.util.List;import javax.naming.ldap.UnsolicitedNotificationListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendduation.demo.Entity.Donation;
import com.backendduation.demo.Entity.Solicitacao;
import com.backendduation.demo.Entity.SolicitacaoRole;
import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Repository.DonationRepository;
import com.backendduation.demo.Repository.SolicitacaoRepository;
import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.DonationService;
import com.backendduation.demo.Service.SolicitacaoService;
import com.backendduation.demo.Service.UserService;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {
	
	@Autowired
	UserService userservice;
	@Autowired
	SolicitacaoService solservice;
	
	@Autowired
	DonationService donationservice;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	DonationRepository donationrepository;
	
	
	
	@GetMapping(value ="/listatodos")
	public List<Solicitacao> findAll(){		
		return solservice.findAll();		
	}
	
	
	@PatchMapping("/acepptSolicitation/id={solicitacao_id}/doadorid={doador_id}/receptorid={receptor_id}/donationid={donation_id}")
	public ResponseEntity aceitarSolicitacao(@PathVariable("solicitacao_id") Long idsolicitacao,
			@PathVariable("doador_id") Long iddoador,
	        @PathVariable("receptor_id") Long idreceptor,
	        @PathVariable("donation_id") Long iddonation) {
		Solicitacao solicitation=solicitacaoRepository.findById(idsolicitacao).orElseThrow(()->new IllegalArgumentException("Id  solicitante nao encontrado"));
		List<Solicitacao> listaSolicitaçoes = new ArrayList<>(); 
		listaSolicitaçoes=solservice.findAll();	
		if(solicitation.getRole().equals(SolicitacaoRole.AGUARDANDO)){
			User solicitante=userrepository.findById(idreceptor).orElseThrow(()->new IllegalArgumentException("Id  solicitante nao encontrado"));
			User doador=userrepository.findById(iddoador).orElseThrow(()->new IllegalArgumentException("Id doador nao encontrado"));
			Donation doation=donationrepository.findById(iddonation).orElseThrow(()->new IllegalArgumentException("Id  donation nao encontrado"));		
			solicitation.setRole(SolicitacaoRole.ACEITA);
			if(solicitation.getRole().equals(SolicitacaoRole.ACEITA)) {
				solicitante.getDoacoes().add(doation);
				doation.setUsuario(solicitante);
				solicitacaoRepository.save(solicitation);				
				return ResponseEntity.ok().body("Solicitação Aceita Por favor entre em contato com o Doador "+doador.getNome()+" pelo e-mail "+doador.getEmail());
			}
		}				
		return ResponseEntity.badRequest().build();
	}
	
	
		@PatchMapping("/rejeitarSolicitation/id={solicitacao_id}")
		public ResponseEntity rejeitarSolicitacao(@PathVariable("solicitacao_id") Long idsolicitacao) {
		Solicitacao solicitation=solicitacaoRepository.findById(idsolicitacao).orElseThrow(()->new IllegalArgumentException("Id  solicitante nao encontrado"));
			solicitation.setRole(SolicitacaoRole.RECUSADA);
			return ResponseEntity.ok().body("Solicitação Recusada");
		}
	
	@PostMapping("/realizarsolicitacao/doadorid={doador_id}/receptorid={receptor_id}/donationid={donation_id}")
	public ResponseEntity insert(@RequestBody @Validated Solicitacao solicitacao,
	        @PathVariable("doador_id") Long iddoador,
	        @PathVariable("receptor_id") Long idreceptor,
	        @PathVariable("donation_id") Long iddonation) {
		
		User solicitante=userrepository.findById(idreceptor).orElseThrow(()->new IllegalArgumentException("Id  solicitante nao encontrado"));
		User doador=userrepository.findById(iddoador).orElseThrow(()->new IllegalArgumentException("Id doador nao encontrado"));
		Donation doation=donationrepository.findById(iddonation).orElseThrow(()->new IllegalArgumentException("Id  donation nao encontrado"));
					
				
		solicitacao.setDestinatario(doador);
		doation.getDonationSolicitadas().add(solicitacao);
		solicitacao.setSolicitante(solicitante);
		solicitacao.setSolicita_donations(doation);
		solicitante.getSolicitacoesEnviadas().add(solicitacao);
		doador.getSolicitacoesRecebidas().add(solicitacao);
		solicitacao.setRole(SolicitacaoRole.AGUARDANDO);
		solicitacao=solservice.insert(solicitacao);
		return ResponseEntity.ok().body(solicitacao);								
				
	}
			
	
	
	
	


}
