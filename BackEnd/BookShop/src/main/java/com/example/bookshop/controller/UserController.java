package com.example.bookshop.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.service.UserService;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostConstruct
	public void initRolesAndUsers() {
		service.initRolesAndUsers();
	}
	
	@GetMapping("/forUser")
	public String forUser() {
		return "This URL is only accessible to USER";
	}
	
	
	

}
