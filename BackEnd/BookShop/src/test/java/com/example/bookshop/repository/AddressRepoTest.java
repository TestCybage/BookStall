package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

@SpringBootTest
class AddressRepoTest {

	@Autowired
	private AddressRepo dao;

	@Autowired
	private RoleRepo roleDao;

	@Autowired
	private UserRepo userDao;

	private Role role = new Role("Tester", "For test purpose");

	private Set<Role> roles = new HashSet<>();

	private Users user = new Users("tester1", "test", "test@test.com", "test1234", "8877554422", UserStatus.ENABLED,
			null, null, null);

	private Address address = new Address(100, "testhub", "testcity", 112233, user);

	private List<Address> addresses = new ArrayList<>();
	
	private List<Address> testAddresses;

	@AfterEach
	void clear() {
		dao.deleteById(testAddresses.get(0).getId());
		userDao.delete(user);
		roleDao.delete(role);
	}

	@Test
	void testFindByUser() {
		roles.add(role);
		roleDao.save(role);
		user.setRole(roles);
		userDao.save(user);
		addresses.add(address);
		user.setAddress(addresses);
		dao.save(address);
		testAddresses = dao.findByUser(user);
		assertNotNull(testAddresses);
		assertEquals(address.getAddressLine(), testAddresses.get(0).getAddressLine());
	}

}
