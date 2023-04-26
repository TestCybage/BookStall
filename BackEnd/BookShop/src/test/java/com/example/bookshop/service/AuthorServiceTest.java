package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.AuthorRepo;

@SpringBootTest
class AuthorServiceTest {

	@Autowired
	private AuthorService service;

	@MockBean
	private AuthorRepo dao;
	
	@MockBean
	private BookService bookService;
	
	int id = 100;
	
	String name = "Aashay";

	@Test
	void testGetAuthorById() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		Optional<Author> author1 = Optional.ofNullable(author);
		when(dao.findById(id)).thenReturn(author1);
		assertThat(service.getAuthorById(id)).isEqualTo(author);
	}

	@Test
	void testAddAuthor() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		
		when(dao.save(author)).thenReturn(author);
		assertThat(service.addAuthor(author)).isEqualTo(author);
	}
	@Test
	void testAddAuthorAlreadyExists() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		when(service.getByAuthorName(name)).thenReturn(author);
		assertThrows(AlreadyExistException.class, ()->service.addAuthor(author));
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

		when(dao.findAll()).thenReturn(authors);
		assertThat(service.getAllAuthor()).isEqualTo(authors);
	}

	@Test
	void testGetAllAuthorRecordsEmpty() {
		List<Author> authors = new ArrayList<>();
		when(dao.findAll()).thenReturn(authors);
		assertThrows(EmptyRecordException.class, ()->service.getAllAuthor());
	}

	@Test
	void testGetByAuthorName() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		when(dao.findByAuthorName(name.toUpperCase())).thenReturn(author);
		assertThat(service.getByAuthorName(name)).isEqualTo(author);
	}

	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setAuthorId(id);
		author.setAuthorName(name);
		Book book1 = new Book(100, "none", author, "none", 200, 0, 100, 2);
		Book book2 = new Book(101, "none1", author, "none1", 200, 0, 100, 2);
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		Optional<Author> author1 = Optional.ofNullable(author);
		when(dao.findById(id)).thenReturn(author1);
		when(bookService.deleteBook(book1.getBookId())).thenReturn(true);
		when(bookService.deleteBook(book2.getBookId())).thenReturn(true);
		assertTrue(service.deleteAuthor(id));	
		verify(dao,times(1)).delete(author);
	}

	@Test
	void testDeleteAuthorNotFound() {
		Optional<Author> emptyAuthor = Optional.empty();
		when(dao.findById(id)).thenReturn(emptyAuthor);
		assertThrows(RecordNotFoundException.class, ()->service.deleteAuthor(id));
	}
	

}
