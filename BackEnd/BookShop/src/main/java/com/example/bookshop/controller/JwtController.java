package com.example.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.JwtRequest;
import com.example.bookshop.entities.JwtResponse;
import com.example.bookshop.service.JwtService;
import com.example.bookshop.service.UserService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService service;

	@PostMapping("/authenticate")
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
		
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<UserDto> signUp(@RequestBody UserDto dto){
		return new ResponseEntity<>(UserDto.toDto(service.signUp(UserDto.toEntity(dto))), HttpStatus.CREATED);
	}
}
