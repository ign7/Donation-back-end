package com.backendduation.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendduation.demo.Entity.Donation;
import com.backendduation.demo.enums.Categoria;

public interface DonationRepository extends JpaRepository<Donation,Long > {
	
	List<Donation> findBynome(String nome);
	List<Donation> findBycategoria(Categoria categoria);
	

}
