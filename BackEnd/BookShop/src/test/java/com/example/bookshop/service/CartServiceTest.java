package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.CartRepo;
@SpringBootTest
class CartServiceTest {
	
	@MockBean
	private CartRepo dao;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private BookService bookService;
	
	@Autowired
	private CartService service;
	
	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;
	
	private String userId = "user1@example.com";
	
	private String bookName ="TestBook";

	private Author author;

	private Book book;

	private Map<String, Integer> bookMap = new HashMap<>();

	private Cart cart;
	
	@BeforeEach
	void setUp() {
		roles.add(role);
		user = new Users(userId, "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		author = new Author(100, "Tester", null, 2);
		book = new Book(200, bookName, author, "Test", 100, 100, 200, 2);
		bookMap.put(book.getBookName(), 2);
		cart = new Cart(101, bookMap, 400, user);
	}

	@Test
	void testGetCartByUserName() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		Cart result = service.getCartByUserName(userId);
		assertEquals(cart, result);
	}
	
	@Test
	void testGetCartByUserNameNotFound() {
		when(userService.getById(userId)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.getCartByUserName(userId));
	}

	@Test
	void testAddToCart() {
		int bookId =200;
		int quantity = 2;
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		
		Map<String, Integer> books = cart.getBooks();
		Set<String> bookNames = books.keySet();
		
		when(bookService.getBookById(bookId)).thenReturn(book);
		
		if (books.containsKey(book.getBookName()))
			books.replace(book.getBookName(), books.get(book.getBookName()) + quantity);
		else
			books.putIfAbsent(book.getBookName(), quantity);
		double amount = 0;
		when(bookService.getBookByName(bookName)).thenReturn(book);
		for (String name : bookNames) {
			amount = amount + bookService.getBookByName(name).getPrice() * books.get(name);
		}
		cart.setBooks(books);
		cart.setAmount(amount);
		when(dao.save(cart)).thenReturn(cart);
		Cart result = service.addToCart(bookId, quantity, userId);
		assertNotNull(result);
		assertEquals(cart, result);
	}
	
	@Test
	void testAddToCartCase2() {
		Book book1 = new Book(201, "One Piece", author, "Luffy", 10, 10, 100, 5);
		int quantity = 1;
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		
		Map<String, Integer> books = cart.getBooks();
		Set<String> bookNames = books.keySet();
		
		when(bookService.getBookById(201)).thenReturn(book1);
		
		if (books.containsKey(book.getBookName()))
			books.replace(book.getBookName(), books.get(book.getBookName()) + quantity);
		else
			books.putIfAbsent(book.getBookName(), quantity);
		double amount = 0;
		when(bookService.getBookByName(bookName)).thenReturn(book);
		when(bookService.getBookByName("One Piece")).thenReturn(book1);
		for (String name : bookNames) {
			amount = amount + bookService.getBookByName(name).getPrice() * books.get(name);
		}
		cart.setBooks(books);
		cart.setAmount(amount);
		when(dao.save(cart)).thenReturn(cart);
		Cart result = service.addToCart(201, quantity, userId);
		assertNotNull(result);
		assertEquals(cart, result);
	}
	
	@Test
	void testAddToCartWithExceptions() {
		int bookId =0;
		int quantity = 2;
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(null);
		TestCreateCart();
		when(bookService.getBookById(bookId)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.addToCart(0, quantity, userId));
	}

	@Test
	void testEmptyCart() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		cart.setBooks(new HashMap<>());
		cart.setAmount(0);
		cart.setUser(user);
		when(dao.save(cart)).thenReturn(cart);
		Cart result = service.emptyCart(userId);
		assertNotNull(result);
		assertEquals(result, cart);
	}
	
	@Test
	void testEmptyCartNotFound() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.emptyCart(userId));
	}
	
	@Test
	void TestCreateCart() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(null);
		when(userService.getById(userId)).thenReturn(user);
		Cart cart = new Cart();
		cart.setBooks(new HashMap<>());
		cart.setAmount(0);
		cart.setUser(user);
		when(dao.save(cart)).thenReturn(cart);
		Cart result = service.createCart(userId);
		assertNotNull(result);
		assertEquals(cart, result);
	}
	
	@Test
	void TestCreateCartAlreadyExists() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		assertThrows(AlreadyExistException.class, ()->service.createCart(userId));
	}

}
