package com.backendduation.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendduation.demo.Entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
   
	Material findBynome(String nome);
	
}
