package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
@SpringBootTest
class CartRepoTest {
	
	@Autowired
	private CartRepo dao;
	
	@Autowired
	private RoleRepo roleDao;
	
	@Autowired
	private UserRepo userDao;
	
	@Autowired
	private BookRepo bookDao;
	
	@Autowired
	private AuthorRepo authorDao;
	
	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;

	private Author author;

	private Book book;

	private Map<String, Integer> bookMap = new HashMap<>();

	private Cart cart;
	
	private Cart testCart;
	
	@BeforeEach
	void setup() {
		roles.add(role);
		role = roleDao.save(role);
		user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		user = userDao.save(user);
		author = new Author(100, "Tester", null, 2);
		author = authorDao.save(author);
		book = new Book(200, "TestBook", author, "Test", 100, 100, 200, 2);
		book = bookDao.save(book);
		bookMap.put(book.getBookName(), 2);
		cart = new Cart();
		cart.setBooks(bookMap);
		cart.setAmount(400);
		cart.setUser(user);
		cart = dao.save(cart);
	}
	
	@AfterEach
	void cleanUp() {
		bookDao.delete(book);
		authorDao.delete(author);
		dao.delete(cart);
		userDao.delete(user);
		roleDao.delete(role);
	}

	@Test
	void testFindByUser() {
		testCart = dao.findByUser(user);
		assertNotNull(testCart);
		assertEquals(user.getUserName(), testCart.getUser().getUserName());
	}

}
