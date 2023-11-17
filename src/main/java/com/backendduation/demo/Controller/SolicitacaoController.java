package com.backendduation.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.backendduation.demo.Entity.SolicitacaoDTO;
import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Repository.DonationRepository;
import com.backendduation.demo.Repository.SolicitacaoRepository;
import com.backendduation.demo.Repository.UserRepository;
import com.backendduation.demo.Service.DonationService;
import com.backendduation.demo.Service.SolicitacaoService;
import com.backendduation.demo.Service.UserService;
import com.backendduation.demo.enums.DonationRole;
import com.backendduation.demo.enums.SolicitacaoRole;

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

	@GetMapping(value = "/listatodos")
	public List<Solicitacao> findAll() {
		return solservice.findAll();
	}

	@GetMapping(value = "/solicitacaoporid/byid={id}")
	public ResponseEntity<SolicitacaoDTO> findbySolicitacaoId(@PathVariable Long id) {
		Solicitacao obj = solicitacaoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id  solicitante nao encontrado"));
		Long id_donation = obj.getSolicita_donations().getId();
		Long id_doador = obj.getDestinatario().getId();
		Long id_receptor = obj.getSolicitante().getId();
		SolicitacaoDTO solicitacaoDTO = new SolicitacaoDTO(obj, id_donation, id_receptor, id_doador);
		return ResponseEntity.ok().body(solicitacaoDTO);
	}

	@PatchMapping("/acepptSolicitation/solicitacaoid={solicitacao_id}/doadorid={doador_id}/receptorid={receptor_id}/donationid={donation_id}")
	public ResponseEntity aceitarSolicitacao(@PathVariable("solicitacao_id") Long idsolicitacao,
			@PathVariable("doador_id") Long iddoador, @PathVariable("receptor_id") Long idreceptor,
			@PathVariable("donation_id") Long iddonation) {
		Solicitacao solicitation = solicitacaoRepository.findById(idsolicitacao)
				.orElseThrow(() -> new IllegalArgumentException("Id  solicitante nao encontrado"));
		List<Solicitacao> listaSolicitacoes = new ArrayList<>();
		if (solicitation.getRole().equals(SolicitacaoRole.AGUARDANDO)) {
			User solicitante = userrepository.findById(idreceptor)
					.orElseThrow(() -> new IllegalArgumentException("Id  solicitante nao encontrado"));
			User doador = userrepository.findById(iddoador)
					.orElseThrow(() -> new IllegalArgumentException("Id doador nao encontrado"));
			Donation doation = donationrepository.findById(iddonation)
					.orElseThrow(() -> new IllegalArgumentException("Id  donation nao encontrado"));
			solicitation.setRole(SolicitacaoRole.ACEITA);
			if (solicitation.getRole().equals(SolicitacaoRole.ACEITA)) {
				solicitante.getDoacoes().add(doation);
				doation.setStatus(DonationRole.ENCERRADA);
				doation.setUsuario(solicitante);
				listaSolicitacoes = doation.getDonationSolicitadas();
				for (Solicitacao solicitacoesdestadoacao : listaSolicitacoes) {
					if (solicitacoesdestadoacao.getRole().equals(SolicitacaoRole.AGUARDANDO)) {
						solicitacoesdestadoacao.setRole(SolicitacaoRole.RECUSADA);
					}
				}
				solicitacaoRepository.save(solicitation);
				return ResponseEntity.ok()
						.body("Solicitação Aceita Por favor entre em contato com o Doador " + doador.getNome()
								+ " pelo e-mail " + doador.getEmail() + " ou pelo telefone " + doador.getTelefone());
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@PatchMapping("/rejeitarSolicitation/id={solicitacao_id}")
	public ResponseEntity rejeitarSolicitacao(@PathVariable("solicitacao_id") Long idsolicitacao) {
		Solicitacao solicitation = solicitacaoRepository.findById(idsolicitacao)
				.orElseThrow(() -> new IllegalArgumentException("Id  solicitante nao encontrado"));
		solicitation.setRole(SolicitacaoRole.RECUSADA);
		return ResponseEntity.ok().body("Solicitação Recusada");
	}

	@PostMapping("/realizarsolicitacao/doadorid={doador_id}/receptorid={receptor_id}/donationid={donation_id}")
	public ResponseEntity insert(@RequestBody @Validated Solicitacao solicitacao,
			@PathVariable("doador_id") Long iddoador, @PathVariable("receptor_id") Long idreceptor,
			@PathVariable("donation_id") Long iddonation) {
		if (!iddoador.equals(idreceptor)) {
			User solicitante = userrepository.findById(idreceptor)
					.orElseThrow(() -> new IllegalArgumentException("Id  solicitante nao encontrado"));
			User doador = userrepository.findById(iddoador)
					.orElseThrow(() -> new IllegalArgumentException("Id doador nao encontrado"));
			Donation doation = donationrepository.findById(iddonation)
					.orElseThrow(() -> new IllegalArgumentException("Id  donation nao encontrado"));
			solicitacao.setDestinatario(doador);
			doation.getDonationSolicitadas().add(solicitacao);
			solicitacao.setSolicitante(solicitante);
			solicitacao.setSolicita_donations(doation);
			solicitante.getSolicitacoesEnviadas().add(solicitacao);
			doador.getSolicitacoesRecebidas().add(solicitacao);
			solicitacao.setRole(SolicitacaoRole.AGUARDANDO);
			List<Solicitacao> solicitacoes = solservice.findAll();
			for (Solicitacao sol : solicitacoes) {
				if (sol.getSolicitante().getId().equals(idreceptor)
						&& sol.getSolicita_donations().getId().equals(iddonation)) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Ops... Voce so pode solicitar uma vez !!");
				}
			}
			solicitacao = solservice.insert(solicitacao);
			return ResponseEntity.ok().body(solicitacao);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Ops... Voce nao pode solicitar sua propria doação !!");
	}

}
