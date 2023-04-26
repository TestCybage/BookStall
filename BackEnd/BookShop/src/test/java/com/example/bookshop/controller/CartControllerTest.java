package com.example.bookshop.controller;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.bookshop.dto.CartDto;
import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.service.CartService;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

	@Mock
	private CartService service;

	@InjectMocks
	private CartController controller;

	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;

	private Author author;

	private Book book;

	private Map<String, Integer> bookMap = new HashMap<>();

	private Cart cart;

	private CartDto cartDto;

	@BeforeEach
	void setUp() {
		roles.add(role);
		user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		author = new Author(100, "Tester", null, 2);
		book = new Book(200, "TestBook", author, "Test", 100, 100, 200, 2);
		bookMap.put(book.getBookName(), 2);
		cart = new Cart(101, bookMap, 400, user);
		cartDto = CartDto.toDto(cart);
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddToCart() {
		when(service.addToCart(200, 2, user.getUserName())).thenReturn(cart);
		ResponseEntity<CartDto> response = controller.addToCart(200, 2, user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(cartDto, response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testEmptyCart() {
		when(service.emptyCart(user.getUserName())).thenReturn(null);
		ResponseEntity<String> response = controller.emptyCart(user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Cart is Emptied", response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetCart() {
		when(service.getCartByUserName(user.getUserName())).thenReturn(cart);
		ResponseEntity<CartDto> response = controller.getCart(user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(cartDto, response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetCart_EmptyCart() {
		when(service.getCartByUserName(user.getUserName())).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->controller.getCart("user1@example.com"));
	}

}
