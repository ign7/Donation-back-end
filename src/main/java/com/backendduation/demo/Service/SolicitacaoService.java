package com.backendduation.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.backendduation.demo.Entity.Solicitacao;
import com.backendduation.demo.Repository.SolicitacaoRepository;


@Service
public class SolicitacaoService {
	
	@Autowired
    SolicitacaoRepository repository;
	
	public List<Solicitacao> findAll(){
		return repository.findAll();
	}

	public Solicitacao insert(Solicitacao obj) {	
		return repository.save(obj); 
	}
	
	
	public void delete(long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
