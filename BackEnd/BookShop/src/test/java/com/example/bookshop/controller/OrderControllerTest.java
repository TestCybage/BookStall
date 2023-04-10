package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

import com.example.bookshop.dto.OrdersDto;
import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	@Mock
	private OrderService service;

	@InjectMocks
	private OrderController controller;

	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;

	private Address address;

	private Book book;

	private Author author;

	private Orders order;
	
	private List<Orders> listOrder = new ArrayList<>();
	
	private List<OrdersDto> listOrderDto = new ArrayList<>();

	private Map<String, Integer> bookMap = new HashMap<>();

	private OrdersDto orderDto;

	@BeforeEach
	void setUp() {
		roles.add(role);
		user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		address = new Address(101, "Deccan", "Pune", 411014, user);
		author = new Author(100, "Tester", null, 2);
		book = new Book(200, "TestBook", author, "Test", 100, 100, 200, 2);
		bookMap.put(book.getBookName(), 2);
		order = new Orders(121, bookMap, address, OrderStatus.COMPLETED, 400, user);
		listOrder.add(order);
		orderDto = OrdersDto.toDto(order);
		listOrderDto.add(orderDto);
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetOrderByUserName() {
		when(service.getOrderByUserName(user.getUserName())).thenReturn(listOrder);
		ResponseEntity<List<OrdersDto>> response = controller.getOrderByUserName(user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(listOrderDto, response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddOrder() {
		when(service.addOrder(user.getUserName(), address.getId())).thenReturn(order);
		ResponseEntity<OrdersDto> response = controller.addOrder(user.getUserName(), address.getId());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(orderDto, response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testCancelOrder() {
		order.setStatus(OrderStatus.CANCELLED);
		orderDto.setStatus(OrderStatus.CANCELLED);
		when(service.cancelOrder(order.getOrderId())).thenReturn(order);
		ResponseEntity<OrdersDto> response = controller.cancelOrder(order.getOrderId());
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(orderDto, response.getBody());
	}

}
