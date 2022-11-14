package com.example.bookshop.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.service.UserService;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
		Users user  = service.getUserByEmail(email);
		if(user==null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		return new ResponseEntity<>(UserDto.toDto(user), HttpStatus.OK);
	}
	
	@PostConstruct
	public void initRolesAndUsers() {
		service.initRolesAndUsers();
	}
	
	@GetMapping("/forUser")
	public String forUser() {
		return "This URL is only accessible to USER";
	}
	

}
