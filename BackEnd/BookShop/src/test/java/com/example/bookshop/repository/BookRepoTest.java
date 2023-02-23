package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;

@SpringBootTest
class BookRepoTest {
	
	@Autowired
	private BookRepo dao;
	
	@Autowired
	private AuthorRepo authorDao;
	
	String bookName = "Alchemist";
	String authorName = "Paolo";
	String searchBook = "chemist";
	Double price = 850d;
	
	@BeforeEach
	void setup() {
		Book book = new Book();
		Author author = new Author();
		author.setAuthorName(authorName);
		authorDao.save(author);
		book.setBookName(bookName);
		book.setAuthor(author);
		book.setPrice(price);
		dao.save(book);
	}
	@AfterEach
	void clear() {
		Book book = dao.findByBookName(bookName);
		dao.delete(book);
		authorDao.delete(book.getAuthor());
	}

	@Test
	void testFindByBookName() {
		Book testBook = dao.findByBookName(bookName);
		assertNotNull(testBook);
		assertEquals(bookName, testBook.getBookName());
	}

	@Test
	@Transactional
	void testFindByAuthor() {
		Author testAuthor = new Author();
		testAuthor.setAuthorName(authorName);
		Book testBook = new Book();
		testBook.setBookName(bookName);
		testBook.setPrice(price);
		testBook.setAuthor(testAuthor);
		List<Book> testBooks = new ArrayList<>();
		testBooks.add(testBook);
		Author author = authorDao.findByAuthorName(authorName);
		assertNotNull(author);
		assertEquals(author.getAuthorName(), testAuthor.getAuthorName());
		List<Book> books = dao.findByAuthor(author);
		assertNotNull(books);
		List<String> bookNames =  books.stream().map(book -> book.getBookName()).toList();
		List<String> testBookNames = testBooks.stream().map(testBook1 -> testBook1.getBookName()).toList();
		assertEquals(bookNames, testBookNames);
	}

	@Test
	void testFindByBookNameContaining() {
		Book testBook = new Book();
		testBook.setBookName(bookName);
		testBook.setPrice(price);
		Author testAuthor = new Author();
		testAuthor.setAuthorName(authorName);
		testBook.setAuthor(testAuthor);
		List<Book> testBooks = new ArrayList<>();
		testBooks.add(testBook);
		List<String> testBookNames = testBooks.stream().map(name -> name.getBookName()).toList();
		List<Book> books = dao.findByBookNameContaining(searchBook);
		assertNotNull(books);
		List<String> bookNames = books.stream().map(name -> name.getBookName()).toList();
		assertEquals(testBookNames, bookNames);
	}

}
