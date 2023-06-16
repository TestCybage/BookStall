package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.BookRepo;

@SpringBootTest
class BookServiceTest {

	@MockBean
	private BookRepo dao;

	@Autowired
	private BookService service;

	@MockBean
	private AuthorService authorService;

	Logger log = Logger.getLogger(BookServiceTest.class);

	int id = 100;
	String bookName = "TestBook";
	int bookPrice = 500;
	String authorName = "TestAuthor".toUpperCase();
	Book book = new Book();
	Author author = new Author();
	List<Book> books = new ArrayList<>();

	@BeforeEach
	void setup() {
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
		when(dao.findAll()).thenReturn(books);
		assertThat(service.getAllBooks()).isEqualTo(books);
	}

	@Test
	void testGetAllBooksEmptyRecords() {
		List<Book> emptyList = new ArrayList<>();
		when(dao.findAll()).thenReturn(emptyList);
		assertThrows(EmptyRecordException.class, () -> service.getAllBooks());
	}

	@Test
	void testGetBookById() {
		Optional<Book> book1 = Optional.ofNullable(book);
		when(dao.findById(id)).thenReturn(book1);
		assertThat(service.getBookById(id)).isEqualTo(book);
	}

	@Test
	void testGetBookByName() {
		when(dao.findByBookName(bookName.toUpperCase())).thenReturn(book);
		assertThat(service.getBookByName(bookName)).isEqualTo(book);
	}

	@Test
	void testAddBookAlreadyExists() {
		String upperCaseName = book.getBookName().toUpperCase();
		book.setBookName(upperCaseName);
		when(dao.findByBookName(upperCaseName)).thenReturn(book);
		assertThrows(AlreadyExistException.class, () -> service.addBook(book));
	}

	@Test
	void testAddBookWithAuthor() {
		String upperCaseName = book.getBookName().toUpperCase();
		book.setBookName(upperCaseName);
		when(dao.findByBookName(upperCaseName)).thenReturn(null);
		when(authorService.getByAuthorName(authorName)).thenReturn(author);
		book.setAuthor(author);
		assertEquals(author, book.getAuthor());
		when(dao.save(book)).thenReturn(book);
		assertThat(service.addBook(book)).isEqualTo(book);
	}

	@Test
	void testAddBookWithoutAuthor() {
		String upperCaseName = book.getBookName().toUpperCase();
		book.setBookName(upperCaseName);
		when(dao.findByBookName(upperCaseName)).thenReturn(null);
		when(authorService.getByAuthorName(authorName)).thenReturn(null);
		book.getAuthor().setAuthorName(book.getAuthor().getAuthorName());
		assertEquals(authorName, book.getAuthor().getAuthorName());
		when(authorService.addAuthor(author)).thenReturn(author);
		assertEquals(author, book.getAuthor());

		when(dao.save(book)).thenReturn(book);
		assertThat(service.addBook(book)).isEqualTo(book);
	}

	@Test
	void testEditPrice() {
		Optional<Book> book1 = Optional.ofNullable(book);
		when(dao.findById(id)).thenReturn(book1);
		book1.get().setPrice(600);
		assertEquals(600, book1.get().getPrice());
		when(dao.save(book)).thenReturn(book);
		assertEquals(service.editPrice(id, 600), book1.get());
	}

	@Test
	void testEditPriceBookNotFound() {
		Optional<Book> book1 = Optional.empty();
		when(dao.findById(id)).thenReturn(book1);
		assertThrows(RecordNotFoundException.class, () -> service.editPrice(id, bookPrice));
	}

	@Test
	void testEditQuantity() {
		int quantity = 1000;
		Optional<Book> book1 = Optional.ofNullable(book);
		when(dao.findById(id)).thenReturn(book1);
		book1.get().setInStock(quantity);
		assertEquals(quantity, book1.get().getInStock());
		when(dao.save(book)).thenReturn(book);
		assertThat(service.editQuantity(id, quantity)).isEqualTo(book1.get());
	}

	@Test
	void testEditQuantityBookNotFound() {
		int quantity = 1000;
		int id = 1357;
		Optional<Book> book1 = Optional.empty();
		when(dao.findById(id)).thenReturn(book1);
		book1=null;
		assertThrows(RecordNotFoundException.class, () -> service.editQuantity(id, quantity));
	}

	@Test
	void testEditQuantityInvalidInput() {
		int quantity = -1000;
		assertThrows(InvalidInputException.class, () -> service.editQuantity(id, quantity));
	}

	@Test
	void testChangeCopiesSold() {
		int copies = 4;
		Optional<Book> book1 = Optional.ofNullable(book);
		when(dao.findById(id)).thenReturn(book1);
		int copiesSold = book1.get().getCopiesSold() + copies;
		book1.get().setCopiesSold(copiesSold);
		book.setCopiesSold(copiesSold);
		assertEquals(copiesSold, book1.get().getCopiesSold());
		when(dao.save(book1.get())).thenReturn(book);
		Book result = service.changeCopiesSold(id, copies);
		assertEquals(result, book1.get());
	}

	@Test
	void testChangeCopiesSoldInvalidInput() {
		int copies = -123;
		assertThrows(InvalidInputException.class, () -> service.changeCopiesSold(id, copies));
	}

	@Test
	void testChangeCopiesSoldBookNotFound() {
		int copies = 12;
		int id = 257;
		Optional<Book> book1 = Optional.empty();
		when(dao.findById(id)).thenReturn(book1);
		book1=null;
		assertThrows(RecordNotFoundException.class, () -> service.changeCopiesSold(id, copies));
	}

	@Test
	void testDeleteBook() {
		Optional<Book> book1 = Optional.ofNullable(book);
		when(dao.findById(id)).thenReturn(book1);
		Boolean result = service.deleteBook(id);
		assertTrue(result);
		verify(dao,times(1)).deleteById(id);
	}
	
	@Test
	void testDeleteBookNotFound() {
		int id = 123;
		Optional<Book> book1 = Optional.empty();
		when(dao.findById(id)).thenReturn(book1);
		assertThrows(RecordNotFoundException.class, ()->service.deleteBook(id));
		verify(dao,times(0)).deleteById(id);
	}

	@Test
	void testGetBookByAuthorName() {
		when(authorService.getByAuthorName(authorName)).thenReturn(author);
		when(dao.findByAuthor(author)).thenReturn(books);
		assertThat(service.getBookByAuthorName(authorName)).isEqualTo(books);
	}

	@Test
	void testGetBookByAuthorNameAuthorNotFound() {
		when(authorService.getByAuthorName(authorName)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, () -> service.getBookByAuthorName(authorName));
	}
	
	@Test
	void TestSearchBook(){
		when(dao.findByBookNameContaining("Test")).thenReturn(books);
		assertEquals(books, service.searchBook("Test"));
	}
	
	@Test
	void TestSearchBookNotFound(){
		when(dao.findByBookNameContaining("abc")).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.searchBook("abc"));
	}

}
