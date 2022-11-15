package com.example.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
@Repository
public interface UserRepo extends JpaRepository<Users, String> {
	
	public Users findByEmail(String email);
	
	public List<Users> findByStatus(UserStatus status);
}
