package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.service.UserService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/forAdmin")
	public String forAdmin() {
		return "This URL is only accessible to ADMIN";
	}
	
	@PatchMapping("/unblockUser/{email}")
	public ResponseEntity<UserDto> unblockUser(@PathVariable String email){
		return new ResponseEntity<>(UserDto.toDto(service.unblockUser(email)), HttpStatus.OK);
	}
	
	@GetMapping("/getDisabledUsers")
	public ResponseEntity<List<UserDto>> getDisabledUsers(){
		return new ResponseEntity<>(UserDto.toDto(service.getDisabledUsers()), HttpStatus.OK);
	}

}
