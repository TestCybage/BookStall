package com.example.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.UserRole;
import com.example.bookshop.entities.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	
	public List<Users> findByRole(UserRole role);
	
	public Users findByEmail(String email);
	

}
