package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	
	String naruto = "Naruto";
	String onePiece = "One Piece";
	String kishimoto = "Kishimoto";
	String oda = "Oda";
	String random = "random";

	Author author = new Author(5000, kishimoto, null, 5);
	Book book = new Book(123,naruto, author, "Hokage", 1000, 5000, 500, 5);
	Author author1 = new Author(5001, oda, null, 5);
	Book book1 = new Book(124, onePiece, author1, "Luffy", 1000, 5000, 500, 5);
	List<Book> books = new ArrayList<>();
	List<Book> books1 = new ArrayList<>();

	public BookControllerTest() {
		books.add(book);
		books.add(book1);
		books1.add(book);
	}

	@Mock
	private BookService service;

	@InjectMocks
	private BookController controller;

	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetAllBooks() {
		when(service.getAllBooks()).thenReturn(books);
		ResponseEntity<List<BookDto>> response = controller.getAllBooks();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(books), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetBookById() {
		when(service.getBookById(123)).thenReturn(book);
		ResponseEntity<BookDto> response = controller.getBookById(123);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(book), response.getBody());
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetBookById_NotFound() {
		when(service.getBookById(0)).thenReturn(null);
		try {
			controller.getBookById(0);
		} catch (Exception e) {
			assertEquals(ErrorMessage.BOOK_NOT_FOUND, e.getMessage());
		}
	}

	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetBookByName() {
		when(service.getBookByName(naruto)).thenReturn(book);
		ResponseEntity<BookDto> response = controller.getBookByName(naruto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(book), response.getBody());
	}
	
	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetBookByName_NotFound() {
		when(service.getBookByName(random)).thenReturn(null);
		try {
			controller.getBookByName(random);
		} catch (Exception e) {
			assertEquals(ErrorMessage.BOOK_NOT_FOUND, e.getMessage());
		}
	}

	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetBookByAuthorName() {
		when(service.getBookByAuthorName(kishimoto)).thenReturn(books1);
		ResponseEntity<List<BookDto>> response = controller.getBookByAuthorName(kishimoto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(books1), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddBook() {
		when(service.addBook(book)).thenReturn(book);
		ResponseEntity<BookDto> response = controller.addBook(BookDto.toDto(book));
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(book), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testEditPrice() {
		when(service.editPrice(123, 600)).thenReturn(book);
		book.setPrice(600);
		ResponseEntity<BookDto> response = controller.editPrice(123, 600);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(book), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testChangeQuanity() {
		when(service.editQuantity(123, 999)).thenReturn(book);
		book.setInStock(999);
		ResponseEntity<BookDto> response = controller.changeQuanity(123, 999);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BookDto.toDto(book), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteBook() {
		when(service.deleteBook(123)).thenReturn(true);
		ResponseEntity<Boolean> response = controller.deleteBook(123);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody());
	}

}
