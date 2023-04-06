package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

@SpringBootTest
class CartDtoTest {
	
	private Cart cart;
	
	private Role role  = new Role("tester", "test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user = new Users(); 
	
	private Map<String, Integer> bookMap = new HashMap<>();
	
	private CartDto cartDto;
	
	public CartDtoTest() {
		roles.add(role);
		user.setUserName("Tester");
		user.setName("test");
		user.setEmail("test@test.com");
		user.setPassword("test@pass");
		user.setMobileNo("8899775588");
		user.setStatus(UserStatus.ENABLED);
		user.setRole(roles);
		bookMap.put("testBook", 1);
		cart = new Cart(100, bookMap, 1000, user);
		cartDto = new CartDto(100, bookMap, 1000, user);
	}

	@Test
	void testToDto() {
		CartDto dto = CartDto.toDto(cart);
		assertEquals(cartDto, dto);
	}

	@Test
	void testToEntity() {
		Cart entity = CartDto.toEntity(cartDto);
		assertEquals(cart, entity);
	}

}
