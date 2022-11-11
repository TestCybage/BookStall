package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<>(UserDto.toDto(service.getAllUsers()), HttpStatus.OK);
	}

}
