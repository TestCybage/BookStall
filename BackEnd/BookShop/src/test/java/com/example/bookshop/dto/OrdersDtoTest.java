package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

class OrdersDtoTest {
	
	private Role role  = new Role("tester", "test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user = new Users();
	
	private Address address;
	
	private Map<String, Integer> bookMap = new HashMap<>();
	
	private Orders orders;
	
	private OrdersDto ordersDto;
	
	public OrdersDtoTest() {
		roles.add(role);
		user.setUserName("Tester");
		user.setName("test");
		user.setEmail("test@test.com");
		user.setPassword("test@pass");
		user.setMobileNo("8899775588");
		user.setStatus(UserStatus.ENABLED);
		user.setRole(roles);
		
		address = new Address(100, "Pimpri", "Pune", 414824,user);
		
		orders = new Orders(100, bookMap, address, OrderStatus.COMPLETED, 1000, user);
		ordersDto = new OrdersDto(100, bookMap,address, OrderStatus.COMPLETED, 1000, user);
	}

	@Test
	void testToDtoOrders() {
		OrdersDto dto = OrdersDto.toDto(orders);
		assertEquals(ordersDto, dto);
	}

	@Test
	void testToEntityOrdersDto() {
		Orders entity = OrdersDto.toEntity(ordersDto);
		assertEquals(orders, entity);
	}

	@Test
	void testToDtoListOfOrders() {
		List<Orders> entityList = new ArrayList<>();
		entityList.add(orders);
		List<OrdersDto> dtoList = OrdersDto.toDto(entityList);
		assertEquals(1, dtoList.size());
		assertEquals(ordersDto, dtoList.get(0));
	}

	@Test
	void testToEntityListOfOrdersDto() {
		List<OrdersDto> dtoList = new ArrayList<>();
		dtoList.add(ordersDto);
		List<Orders> entityList = OrdersDto.toEntity(dtoList);
		assertEquals(1,entityList.size());
		assertEquals(orders, entityList.get(0));
	}

}
