package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Author;
import com.example.bookshop.repository.AuthorRepo;

@SpringBootTest
class AuthorServiceTest {

	@Autowired
	private AuthorService service;

	@MockBean
	private AuthorRepo dao;
	
	int id = 100;
	
	String name = "Aashay";

	@Test
	void testGetAuthorById() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		Optional<Author> author1 = Optional.ofNullable(author);
		Mockito.when(dao.findById(id)).thenReturn(author1);
		assertThat(service.getAuthorById(id)).isEqualTo(author);
	}

	@Test
	void testAddAuthor() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		
		Mockito.when(dao.save(author)).thenReturn(author);
		assertThat(service.addAuthor(author)).isEqualTo(author);
	}

	@Test
	void testGetAllAuthor() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);

		Author author1 = new Author();
		author1.setAuthorId(101);
		author1.setAuthorName("Tanay");

		List<Author> authors = new ArrayList<>();
		authors.add(author);
		authors.add(author1);

		Mockito.when(dao.findAll()).thenReturn(authors);
		assertThat(service.getAllAuthor()).isEqualTo(authors);
	}

	@Test
	void testGetByAuthorName() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		
		Mockito.when(dao.findByAuthorName(name.toUpperCase())).thenReturn(author);
		assertThat(service.getByAuthorName(name)).isEqualTo(author);
	}

	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		Optional<Author> author1 = Optional.ofNullable(author);
		Mockito.when(dao.findById(id)).thenReturn(author1);
		assertTrue(service.deleteAuthor(id));		
	}

	

}
