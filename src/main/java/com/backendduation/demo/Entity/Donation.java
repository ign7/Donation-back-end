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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String dataDoacao;	
	@JsonIgnore
	@JoinColumn(name ="user_id")
	@ManyToOne	
	private User usuario;	
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name ="solicitacao_id")		
//	private Donation solicita_donations;	
	
	@OneToMany(mappedBy = "solicita_donations")
	private List<Solicitacao> donationSolicitadas = new ArrayList<>();
	

	
	@JsonIgnore
	@OneToMany(mappedBy = "material")
	private List<Material> materiais = new ArrayList<>();
	
//   public Donation(String DataDoacao) {
//	   this.dataDoacao=DataDoacao;
//   }
	

}
