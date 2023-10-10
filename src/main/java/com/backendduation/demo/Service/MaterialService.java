package com.backendduation.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.backendduation.demo.Entity.Material;
import com.backendduation.demo.Repository.MaterialRepository;

@Service
public class MaterialService {

	
	@Autowired
	MaterialRepository repository;
	
	
	public List<Material> findAll(){
		return repository.findAll();
	}
	
	public Optional<Material> findbyid(long id){
		Optional<Material> obj= repository.findById(id);
		return obj;
	}
	
	public Material insert(Material obj){			
			return repository.save(obj);
	}
		
	public void  delete(long obj) {
		try {
			repository.deleteById(obj);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
}
