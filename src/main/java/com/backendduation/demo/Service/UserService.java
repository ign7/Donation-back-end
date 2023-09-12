package com.backendduation.demo.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendduation.demo.Entity.User;
import com.backendduation.demo.Repository.UserRepository;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public Optional<User> findbyid(long id){
		Optional<User> obj= repository.findById(id);
		return obj;
	}
	
	public User insert(User obj){			
			return repository.save(obj);
	}
		
	public void  delete(long obj) {
		try {
			repository.deleteById(obj);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findByLogin(username);
	}

}
