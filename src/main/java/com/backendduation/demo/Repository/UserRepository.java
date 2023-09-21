package com.backendduation.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.backendduation.demo.Entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
  UserDetails findBylogin(String login);

}
