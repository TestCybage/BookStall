package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.OrderRepo;
@SpringBootTest
class OrderServiceTest {
	
	@MockBean
	private OrderRepo dao;
	
	@Autowired
	private OrderService service;
	
	@MockBean
	private CartService cartService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private EmailService emailService;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private AddressService addressService;
	
	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;

	private Address address;

	private Book book;

	private Author author;

	private Orders order;
	
	private Cart cart;
	
	@SuppressWarnings("unused")
	private Users user1;
	
	private List<Orders> listOrder = new ArrayList<>();

	private Map<String, Integer> bookMap = new HashMap<>();
	
	String userName = "user1@example.com";
	
	@BeforeEach
	void setup() {
		roles.add(role);
		user = new Users(userName, "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		user1 = new Users("user1", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		address = new Address(101, "Deccan", "Pune", 411014, user);
		author = new Author(100, "Tester", null, 2);
		book = new Book(200, "TestBook", author, "Test", 100, 100, 200, 2);
		bookMap.put(book.getBookName(), 2);
		order = new Orders(121, bookMap, address, OrderStatus.COMPLETED, 400, user);
		cart = new Cart(100, bookMap, 400, user);
		listOrder.add(order);
	}
	
	@Test
	void testGetAllOrders() {
		when(dao.findAllByOrderByOrderIdDesc()).thenReturn(listOrder);
		List<Orders> orders = service.getAllOrders();
		assertEquals(listOrder.size(), orders.size());
	}
	
	@Test
	void testGetAllOrdersRecordEmpty() {
		when(dao.findAllByOrderByOrderIdDesc()).thenReturn(null);
		assertThrows(RecordNotFoundException.class,()->service.getAllOrders());
	}

	@Test
	void testGetOrderByUserName() {
		when(userService.getById(userName)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(listOrder);
		List<Orders> result = service.getOrderByUserName(userName);
		assertNotNull(result);
		assertEquals(listOrder, result);
	}
	
	@Test
	void testGetOrderByUserNameUserNotFound() {
		when(userService.getById(userName)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.getOrderByUserName(userName));
	}
	
	@Test
	void testGetOrderByUserNameEmptyList() {
		when(userService.getById(userName)).thenReturn(user);
		List<Orders> list = new ArrayList<>();
		when(dao.findByUser(user)).thenReturn(list);
		assertThrows(EmptyRecordException.class, ()->service.getOrderByUserName(userName));
	}

	@Test
	void testGetById() {
		Optional<Orders> order1 = Optional.ofNullable(order);
		when(dao.findById(121)).thenReturn(order1);
		Orders result = service.getById(121);
		assertEquals(order1.get(), result);
	}

	@Test
	void testAddOrder() {
		when(addressService.getById(101)).thenReturn(address);
		assertEquals(userName, address.getUser().getUserName());
		when(cartService.getCartByUserName(userName)).thenReturn(cart);
		Set<String> bookNames = cart.getBooks().keySet();
		System.out.println(bookNames);
		List<Book> books = new ArrayList<>();
		when(bookService.getBookByName("TestBook")).thenReturn(book);
		for(String i:bookNames) {
			books.add(bookService.getBookByName(i));
		}
		when(dao.save(order)).thenReturn(order);
		for(Book book:books) {
			when(bookService.editQuantity(book.getBookId(), book.getInStock()-cart.getBooks().get(book.getBookName()))).thenReturn(book);
			when(bookService.changeCopiesSold(book.getBookId(), cart.getBooks().get(book.getBookName()))).thenReturn(book);
		}
		when(cartService.emptyCart(userName)).thenReturn(null);
		service.sendInvoice(order);
		verify(emailService).sendEmail(eq("Order Confirmation: Order Number: 121, " + LocalDate.now()),
                eq(userName), anyString());
		Orders result = service.addOrder(userName, 101);
		result.setOrderId(order.getOrderId());
		verify(bookService,times(bookNames.size()*4)).getBookByName(anyString());
		verify(bookService,times(books.size())).editQuantity(anyInt(), anyInt());
		verify(bookService,times(books.size())).changeCopiesSold(anyInt(), anyInt());
		verify(dao,times(1)).save(order);
		assertNotNull(result);
		assertEquals(order, result);
	}
	
	@Test
	void testAddOrderAddressNotFound() {
		when(addressService.getById(100)).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.addOrder(userName, 100));
	}
	
	@Test
	void testAddOrderInvalidAddressId() {
		when(addressService.getById(101)).thenReturn(address);
		assertThrows(InvalidInputException.class, ()->service.addOrder("user1", 101));
	}
	
	@Test
	void testAddOrderEmptyCartCase1() {
		when(addressService.getById(101)).thenReturn(address);
		assertEquals(userName, address.getUser().getUserName());
		when(cartService.getCartByUserName(userName)).thenReturn(null);
		assertThrows(EmptyRecordException.class, ()->service.addOrder(userName, 101));
	}
	
	@Test
	void testAddOrderEmptyCartCase2() {
		cart.setBooks(new HashMap<>());
		when(addressService.getById(101)).thenReturn(address);
		assertEquals(userName, address.getUser().getUserName());
		when(cartService.getCartByUserName(userName)).thenReturn(cart);
		assertThrows(EmptyRecordException.class, ()->service.addOrder(userName, 101));
	}

	@Test
	void testCancelOrder() {
		order.setStatus(OrderStatus.CANCELLED);
		Optional<Orders> order1 = Optional.ofNullable(order);
		when(dao.findById(121)).thenReturn(order1);
		Orders result = service.cancelOrder(121);
		assertNotNull(result);
		assertEquals(OrderStatus.CANCELLED, result.getStatus());
		assertEquals(order1.get(), result);
	}
	
	@Test
	void testCancelOrderNotFound() {
		Optional<Orders> order1 = Optional.empty();
		when(dao.findById(100)).thenReturn(order1);
		assertThrows(RecordNotFoundException.class, ()->service.cancelOrder(100));
	}

	@Test
	void testSendInvoice() {
		when(bookService.getBookByName("TestBook")).thenReturn(book);
		service.sendInvoice(order);
		verify(emailService).sendEmail(eq("Order Confirmation: Order Number: 121, " + LocalDate.now()),
                eq(userName), anyString());
	}

}
