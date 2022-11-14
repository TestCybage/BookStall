package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, String> {
	
	public Users findByEmail(String email);
	

}
