package com.example.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.service.UserService;

@RestController
@RequestMapping("/user")
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
	
	@PostMapping("/signUp")
	public ResponseEntity<UserDto> signUp(@RequestBody UserDto dto){
		return new ResponseEntity<>(UserDto.toDto(service.signUp(UserDto.toEntity(dto))), HttpStatus.CREATED);
	}
	

}
