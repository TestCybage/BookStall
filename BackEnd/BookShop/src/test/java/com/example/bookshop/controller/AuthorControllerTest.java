package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import com.example.bookshop.dto.AuthorDto;
import com.example.bookshop.entities.Author;
import com.example.bookshop.service.AuthorService;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
	
	@Mock
	private AuthorService service;
	
	@InjectMocks
	private AuthorController controller;
	
	String authorName = "Kishimoto"; 

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddAuthor() {
		Author author = new Author(5000, authorName, null, 5);
		when(service.addAuthor(author)).thenReturn(author);
		AuthorDto dto = AuthorDto.toDto(author);
		
		ResponseEntity<AuthorDto> response = controller.addAuthor(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(authorName, response.getBody().getAuthorName());
	}

	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetAllAuthor() {
		Author author = new Author(5000, authorName, null, 5);
		Author author1 = new Author(5001, "Oda", null, 5);
		List<Author> authors = new ArrayList<>();
		authors.add(author);
		authors.add(author1);
		when(service.getAllAuthor()).thenReturn(authors);
		ResponseEntity<List<AuthorDto>> response = controller.getAllAuthor();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(AuthorDto.toDto(authors), response.getBody());
	}

	@Test
	@WithMockUser(authorities = { "USER", "ADMIN" })
	void testGetAuthorByName() {
		Author author = new Author(5000, authorName, null, 5);
		when(service.getByAuthorName(authorName)).thenReturn(author);
		ResponseEntity<AuthorDto> response = controller.getAuthorByName(authorName);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(authorName, response.getBody().getAuthorName());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteAuthor() {
		Author author = new Author(5000, authorName, null, 5);
		when(service.deleteAuthor(author.getAuthorId())).thenReturn(true);
		ResponseEntity<Boolean> response = controller.deleteAuthor(author.getAuthorId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().booleanValue());
	}

}
