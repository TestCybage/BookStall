package com.example.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CartDto;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.service.CartService;

@RestController
@CrossOrigin
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {
	
	@Autowired
	private CartService service;
	
	@PostMapping("/addCart/{quantity}/{userName}")
	public ResponseEntity<CartDto> addToCart(@RequestBody BookDto book,@PathVariable int quantity,@PathVariable String userName){
		Book entity = BookDto.toEntity(book);
		Cart cart = service.addToCart(entity, quantity, userName);
		return new ResponseEntity<>(CartDto.toDto(cart), HttpStatus.OK);
	}
	
	@PutMapping("/emptyCart/{userName}")
	public ResponseEntity<CartDto> emptyCart(@PathVariable String userName){
		return new ResponseEntity<>(CartDto.toDto(service.emptyCart(userName)), HttpStatus.OK);
	}
}
