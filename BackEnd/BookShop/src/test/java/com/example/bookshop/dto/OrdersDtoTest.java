package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
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
	
	private OrdersDto dto1;
	
	private OrdersDto dto2;
	
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
		
		dto1 = new OrdersDto();
		
		dto2 = new OrdersDto();
	}
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(dto1.hashCode(), ordersDto.hashCode());
    }
	
	@Test
    @DisplayName("Test equals method with equal objects")
    void testEqualsWithEqualObjects() {
        assertTrue(dto1.equals(dto2));
    }

    @Test
    @DisplayName("Test equals method with non-equal objects")
    void testEqualsWithNonEqualObjects() {
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(ordersDto));
        assertFalse(dto1.equals(new Object()));
    }
    
    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "OrdersDto";
        String actual = dto1.toString().substring(0, 9);
        assertEquals(expected, actual);
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
