package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
		fail("Not yet Implented");
	}

	@Test
	void testEmptyCart() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(cart);
		assertNull(service.emptyCart(userId));
		verify(dao,times(1)).delete(cart);
	}
	
	@Test
	void testEmptyCartNotFound() {
		when(userService.getById(userId)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.emptyCart(userId));
	}

}
