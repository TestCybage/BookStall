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

import com.example.bookshop.entities.Book;
import com.example.bookshop.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks(){
		return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
	}
	
	@GetMapping("/getBook/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id){
		return new ResponseEntity<>(service.getBookById(id), HttpStatus.OK);
	}
	
	@GetMapping("/getBookByName/{name}")
	public ResponseEntity<List<Book>> getBookByName(@PathVariable String name){
		return new ResponseEntity<>(service.getBookByName(name), HttpStatus.OK);
	}
	
	@PostMapping("/addBook")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		return new ResponseEntity<>(service.addBook(book), HttpStatus.CREATED);
	}
	

}
