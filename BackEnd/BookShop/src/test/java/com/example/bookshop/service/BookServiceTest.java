package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.repository.AuthorRepo;
import com.example.bookshop.repository.BookRepo;

@SpringBootTest
class BookServiceTest {
	
	@MockBean
	private BookRepo dao;
	
	@MockBean
	private AuthorRepo authorDao;
	
	@Autowired
	private BookService service;
	
	@MockBean
	private AuthorService authorService;
	
	Logger log = Logger.getLogger(BookServiceTest.class);
	
	int id = 100;
    String bookName = "TestBook";
    int bookPrice=500;
    String authorName = "TestAuthor".toUpperCase();
    Book book = new Book();
    Author author = new Author();
    List<Book> books = new ArrayList<>(); 
    
    
	public BookServiceTest() {
		book.setBookId(id);
		book.setBookName(bookName);
		book.setPrice(bookPrice);		
		
		author.setAuthorId(id);
		author.setAuthorName(authorName);
		book.setAuthor(author);		
		
		books.add(book);
	}

	@Test
	void testGetAllBooks() {
		Mockito.when(dao.findAll()).thenReturn(books);
		assertThat(service.getAllBooks()).isEqualTo(books);
	}

	@Test
	void testGetBookById() {
		Optional<Book> book1 = Optional.ofNullable(book);
		Mockito.when(dao.findById(id)).thenReturn(book1);
		assertThat(service.getBookById(id)).isEqualTo(book);
	}

	@Test
	void testGetBookByName() {
		Mockito.when(dao.findByBookName(bookName.toUpperCase())).thenReturn(book);
		assertThat(service.getBookByName(bookName)).isEqualTo(book);
	}

	@Test
	void testAddBook() {
		Mockito.when(dao.save(book)).thenReturn(book);
		assertThat(service.addBook(book)).isEqualTo(book);
	}

	@Test
	void testEditPrice() {
		Optional<Book> book1 = Optional.ofNullable(book);
		Mockito.when(dao.findById(id)).thenReturn(book1);
		book.setPrice(600);
		Mockito.when(dao.save(book)).thenReturn(book);
		assertThat(service.getBookById(id)).isEqualTo(book);
	}

	@Test
	void testEditQuantity() {
		Optional<Book> book1 = Optional.ofNullable(book);
		Mockito.when(dao.findById(id)).thenReturn(book1);
		book.setInStock(1000);
		Mockito.when(dao.save(book)).thenReturn(book);
		assertThat(service.getBookById(id)).isEqualTo(book);
	}

	@Test
	void testDeleteBook() {
		Optional<Book> book1 = Optional.ofNullable(book);
		Mockito.when(dao.findById(id)).thenReturn(book1);
		Mockito.when(authorService.getByAuthorName(authorName)).thenReturn(author);
		Mockito.when(authorService.deleteAuthor(id)).thenReturn(true);
		
		assertTrue(service.deleteBook(id));
	}

	@Test
	void testGetBookByAuthorName() {
		Mockito.when(authorService.getByAuthorName(authorName)).thenReturn(author);
		Mockito.when(dao.findByAuthor(author)).thenReturn(books);
		assertThat(service.getBookByAuthorName(authorName)).isEqualTo(books);
	}



}
