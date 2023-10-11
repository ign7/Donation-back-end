package com.backendduation.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.backendduation.demo.Entity.Donation;
import com.backendduation.demo.Repository.DonationRepository;
import com.backendduation.demo.enums.Categoria;



@Service
public class DonationService {
	
	@Autowired
	DonationRepository repository;
	
	public List<Donation> findAll(){
		return repository.findAll();
	}
	
	//public Optional<Donation> findById(Long id) {		
		//Optional<Donation> item = repository.findById(id);
		//return item;		
	//}
	
	public Donation insert(Donation item) {		
		return repository.save(item);
	}
	
	public void delete(long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Donation> findBynome(Categoria categoria) {
		 List<Donation> obj=repository.findBycategoria(categoria);
		 return obj;
	}
	
	public List<Donation> findBynome(String nome) {
		List<Donation> obj=repository.findBynome(nome);
		 return obj;
	}

	public Optional<Donation> findbyid(Long id) {
		Optional<Donation> obj= repository.findById(id);
		return obj;
	}

}
