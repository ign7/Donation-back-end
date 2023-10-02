package com.backendduation.demo.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Solicitacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "solicitante_id")
	private User solicitante;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "destinatario_id")
	private User destinatario;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="solicitacao_id")		
	private Donation solicita_donations;
		
	
//	@OneToMany(mappedBy = "solicita_donations")
//	private List<Donation> donationSolicitadas = new ArrayList<>();
	
	
	

	
	private String dataSolicitacao;
	
    private String observacao;
    
    private SolicitacaoRole role;
       
    public Solicitacao(String dataSolicitacao,String observacao) {
    	this.dataSolicitacao=dataSolicitacao;
    	this.observacao=observacao;
    }

}
