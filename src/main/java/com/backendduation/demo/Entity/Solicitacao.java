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
	private User solicitanteusuario;
		
	
	@OneToMany(mappedBy = "solicitacaodonations")
	private List<Donation> donationSolicitadas = new ArrayList<>();
	
	private String dataSolicitacao;
	
    private String observacao;
    
    private SolicitacaoRole role;
    
   
    private String nomedoador;
    
    
    public Solicitacao(String dataSolicitacao,String observacao) {
    	this.dataSolicitacao=dataSolicitacao;
    	this.observacao=observacao;
    }

}
