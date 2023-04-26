package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.AddressRepo;
@SpringBootTest
class AddressServiceTest {
	
	@MockBean
	private AddressRepo dao;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private AddressService service;

	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
			UserStatus.ENABLED, null, null, roles);
	
	private Users user1 = new Users("tester", "test", "test@example.com", "pass1234", "9988557722",
			UserStatus.ENABLED, null, null, roles);

	private Address address1;

	private List<Address> addresses = new ArrayList<>();
	
	private List<Address> emptyAddresses = new ArrayList<>();
	
	private String userName = "user1@example.com";
	
	@BeforeEach
	void setup() {
		address1 = new Address(100, "Kalyani Nagar", "Pune", 411014, user);
		roles.add(role);
		addresses.add(address1);
		user.setAddress(addresses);
	}

	@Test
	void testGetById() {
		Optional<Address> address = Optional.ofNullable(address1);
		when(dao.findById(100)).thenReturn(address);
		Address address3 = service.getById(100);
		assertNotNull(address3);
		assertEquals(address3, address1);
	}

	@Test
	void testGetByUserName() {
		when(userService.getById(userName)).thenReturn(user);
		when(dao.findByUser(user)).thenReturn(addresses);
		List<Address> listAddress = service.getByUserName(userName);
		assertNotNull(listAddress);
		assertEquals(listAddress, addresses);
	}
	
	@Test
	void testGetByUserNameUserNotFound() {
		when(userService.getById("abc")).thenReturn(null);
		assertThrows(RecordNotFoundException.class,()->service.getByUserName("abc"));
	}
	
	@Test
	void testGetByUserNameRecordsEmpty() {
		when(userService.getById("tester")).thenReturn(user1);
		when(dao.findByUser(user1)).thenReturn(emptyAddresses);
		assertThrows(EmptyRecordException.class, ()->service.getByUserName("tester"));
	}

	@Test
	void testAddAddress() {
		when(userService.getById(userName)).thenReturn(user);
		when(dao.save(address1)).thenReturn(address1);
		Address address = service.addAddress(address1, userName);
		assertNotNull(address);
		assertEquals(address.getUser(), user);
		assertEquals(address, address1);
	}
	
	@Test
	void testAddAddressUserNotFound() {
		when(userService.getById("abc")).thenReturn(null);
		assertThrows(RecordNotFoundException.class,()->service.addAddress(address1, "abc"));
	}

	@Test
	void testDeleteAddress() {
		Optional<Address> address = Optional.ofNullable(address1);
		when(dao.findById(100)).thenReturn(address);
		when(dao.existsById(100)).thenReturn(false);
		Boolean result = service.deleteAddress(userName, 100);
		verify(dao,times(1)).deleteById(100);
		assertTrue(result);
	}
	
	@Test
	void testDeleteAddressNotFound() {
		Optional<Address> nullAddress = Optional.empty();
		when(dao.findById(123)).thenReturn(nullAddress);
		assertThrows(RecordNotFoundException.class, ()->service.deleteAddress(userName, 123));
	}
	
	@Test
	void testDeleteAddressInvalidInput() {
		Optional<Address> address = Optional.ofNullable(address1);
		when(dao.findById(100)).thenReturn(address);
		assertNotEquals("tester", address.get().getUser().getUserName());
		assertThrows(InvalidInputException.class,()->service.deleteAddress("tester", 100));
	}

	@Test
	void testUpdateAddress() {
		Address address = new Address();
		when(userService.getById(userName)).thenReturn(user);
		address.setAddressLine("Pathrot");
		address.setCity("Pathrot");
		address.setPin(444808);
		address.setUser(user);
		address.setId(100);
		when(dao.save(address)).thenReturn(address);
		Address updatedAddress = service.updateAddress(address, userName, 100);
		assertNotNull(updatedAddress);
		assertEquals(address, updatedAddress);
	}
	
	@Test
	void testUpdateAddressUserNotFound() {
		Address address = new Address();
		address.setAddressLine("Pathrot");
		address.setCity("Pathrot");
		address.setPin(444808);
		address.setUser(null);
		address.setId(100);
		when(userService.getById(userName)).thenReturn(null);
		assertThrows(RecordNotFoundException.class,()->service.updateAddress(address1, "abc", 100));
	}

}
