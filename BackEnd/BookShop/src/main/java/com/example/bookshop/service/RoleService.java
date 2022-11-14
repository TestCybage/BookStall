package com.example.bookshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Role;
import com.example.bookshop.repository.RoleRepo;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepo dao;
	
	public Role createNewRole(Role role) {
		return dao.save(role);
	}

}
