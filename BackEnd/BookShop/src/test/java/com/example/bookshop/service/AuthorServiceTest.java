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

	@Test
	void testGetAuthorById() {
		Author author = new Author();
		author.setAuthorId(100);
		author.setAuthorName("Aashay");
		Optional<Author> author1 = Optional.ofNullable(author);
		Mockito.when(dao.findById(100)).thenReturn(author1);
		assertThat(service.getAuthorById(100)).isEqualTo(author);
	}

	@Test
	void testAddAuthor() {
		Author author = new Author();
		author.setAuthorId(100);
		author.setAuthorName("Aashay");
		Mockito.when(dao.save(author)).thenReturn(author);
		assertThat(service.addAuthor(author)).isEqualTo(author);
	}

	@Test
	void testGetAllAuthor() {
		Author author = new Author();
		author.setAuthorId(100);
		author.setAuthorName("Aashay");

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
		author.setAuthorId(100);
		author.setAuthorName("Aashay");
		
		Mockito.when(dao.findByAuthorName("Aashay".toUpperCase())).thenReturn(author);
		assertThat(service.getByAuthorName("Aashay")).isEqualTo(author);
	}

	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setAuthorId(100);
		author.setAuthorName("Aashay");
		Optional<Author> author1 = Optional.ofNullable(author);
		Mockito.when(dao.findById(100)).thenReturn(author1);
	System.out.println("initial list"+author1);
		assertTrue(service.deleteAuthor(100));
		System.out.println("after list"+author1);
		
	}

	

}
