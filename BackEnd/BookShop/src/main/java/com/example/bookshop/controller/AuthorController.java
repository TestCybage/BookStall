package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.entities.Author;
import com.example.bookshop.service.AuthorService;

@RestController
@RequestMapping("/author")
@CrossOrigin("*")
public class AuthorController {
	
	@Autowired
	private AuthorService service;
	
	@PostMapping("/addAuthor")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author){
		return new ResponseEntity<>(service.addAuthor(author), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllAuthors")
	public ResponseEntity<List<Author>> getAllAuthor(){
		return new ResponseEntity<>(service.getAllAuthor(), HttpStatus.OK);
	}
	
	@GetMapping("/getAuthorByName/{name}")
	public ResponseEntity<Author> getAuthorByName(@PathVariable String name){
		return new ResponseEntity<>(service.getByAuthorName(name),HttpStatus.OK);
	}

}
