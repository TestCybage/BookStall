package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.AuthorDto;
import com.example.bookshop.service.AuthorService;

@RestController
@RequestMapping("/author")
@CrossOrigin("*")
public class AuthorController {
	
	@Autowired
	private AuthorService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addAuthor")
	public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto){
		return new ResponseEntity<>(AuthorDto.toDto(service.addAuthor(AuthorDto.toEntity(authorDto))), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/getAllAuthors")
	public ResponseEntity<List<AuthorDto>> getAllAuthor(){
		return new ResponseEntity<>(AuthorDto.toDto(service.getAllAuthor()), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/getAuthorByName/{name}")
	public ResponseEntity<AuthorDto> getAuthorByName(@PathVariable String name){
		return new ResponseEntity<>(AuthorDto.toDto(service.getByAuthorName(name)),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteAuthor/{id}")
	public ResponseEntity<List<AuthorDto>> deleteAuthor(@PathVariable int id){
		return new ResponseEntity<>(AuthorDto.toDto(service.deleteAuthor(id)),HttpStatus.OK);
	}

}
