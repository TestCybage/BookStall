package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.bookshop.dto.AddressDto;
import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.service.AddressService;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {
	
	@Mock
	private AddressService service;
	
	@InjectMocks
	private AddressController controller;
	
	private Role role = new Role("TestUser", "Test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
			UserStatus.ENABLED, null,null, roles);
	
	private Address address1 = new Address(100,"Kalyani Nagar", "Pune", 411014, user);
	
	private Address address2 = new Address(101,"Deccan", "Pune", 411014, user);
	
	private List<Address> addresses = new ArrayList<>();
	
	private AddressDto addressDto1 = new AddressDto(100,"Kalyani Nagar", "Pune", 411014, user);
	
	private AddressDto addressDto2 = new AddressDto(101,"Deccan", "Pune", 411014, user);
	
	private List<AddressDto> listDto = new ArrayList<>();
	
	public AddressControllerTest() {
		roles.add(role);
		addresses.add(address1);
		addresses.add(address2);
		user.setAddress(addresses);
		listDto.add(addressDto1);
		listDto.add(addressDto2);
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetByUserName() {
		when(service.getByUserName("user1@example.com")).thenReturn(addresses);
		ResponseEntity<List<AddressDto>> response = controller.getByUserName("user1@example.com");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(AddressDto.toDto(addresses), response.getBody());
		
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddAddress() {
		when(service.addAddress(address1, "user1@example.com")).thenReturn(address1);
		ResponseEntity<AddressDto> response = controller.addAddress(AddressDto.toDto(address1), "user1@example.com");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(AddressDto.toDto(address1), response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testDeleteAddress() {
		when(service.deleteAddress( "user1@example.com", 100)).thenReturn(true);
		ResponseEntity<Boolean> response = controller.deleteAddress("user1@example.com", 100);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testUpdateAddress() {
		when(service.updateAddress(address1, "user1@example.com", 100)).thenReturn(address1);
		ResponseEntity<AddressDto> response = controller.updateAddress(AddressDto.toDto(address1),  "user1@example.com", 100);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(AddressDto.toDto(address1), response.getBody());
	}

}
