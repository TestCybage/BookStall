package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {

	@Autowired
	private BookService service;

	@GetMapping("/getAllBooks")
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return new ResponseEntity<>(BookDto.toDto(service.getAllBooks()), HttpStatus.OK);
	}

	@GetMapping("/getBook/{id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable int id) {
		return new ResponseEntity<>(BookDto.toDto(service.getBookById(id)), HttpStatus.OK);
	}

	@GetMapping("/getBookByName/{name}")
	public ResponseEntity<BookDto> getBookByName(@PathVariable String name) {
		Book book = service.getBookByName(name);
		if (book==null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		return new ResponseEntity<>(BookDto.toDto(book), HttpStatus.OK);
	}

	@GetMapping("/getBookByAuthorName/{name}")
	public ResponseEntity<List<BookDto>> getBookByAuthorName(@PathVariable String name) {
		return new ResponseEntity<>(BookDto.toDto(service.getBookByAuthorName(name)), HttpStatus.OK);
	}

	@PostMapping("/addBook")
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
		return new ResponseEntity<>(BookDto.toDto(service.addBook(BookDto.toEntity(bookDto))), HttpStatus.CREATED);
	}

	@PatchMapping("/editPrice/{id}/{price}")
	public ResponseEntity<BookDto> editPrice(@PathVariable int id, @PathVariable double price) {
		return new ResponseEntity<>(BookDto.toDto(service.editPrice(id, price)), HttpStatus.ACCEPTED);
	}

	@PatchMapping("/editQuantity/{id}/{quantity}")
	public ResponseEntity<BookDto> changeQuanity(@PathVariable int id, @PathVariable int quantity) {
		return new ResponseEntity<>(BookDto.toDto(service.editQuantity(id, quantity)), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<List<BookDto>> deleteBook(@PathVariable int id) {
		return new ResponseEntity<>(BookDto.toDto(service.deleteBook(id)), HttpStatus.OK);
	}

	@DeleteMapping("/clearAll")
	public ResponseEntity<String> clearRecords() {
		return new ResponseEntity<>(service.clearAllRecords(), HttpStatus.OK);
	}

}
