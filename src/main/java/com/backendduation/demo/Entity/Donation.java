package com.backendduation.demo.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.backendduation.demo.enums.Categoria;
import com.backendduation.demo.enums.DonationRole;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
	private Categoria categoria;
	private DonationRole status;
	@JsonIgnore
	@JoinColumn(name ="user_id")
	@ManyToOne	
	private User usuario;	
	
	@OneToMany(mappedBy = "solicita_donations")
	private List<Solicitacao> donationSolicitadas = new ArrayList<>();		
	
	
	@OneToMany(mappedBy = "material")
	private List<Material> materiais = new ArrayList<>();
	

	

}
