package com.backendduation.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.backendduation.demo.Entity.Donation;
import com.backendduation.demo.Repository.DonationRepository;



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

}
