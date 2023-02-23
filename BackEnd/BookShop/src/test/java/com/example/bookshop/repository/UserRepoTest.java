package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

@SpringBootTest
class UserRepoTest {

	@Autowired
	private UserRepo dao;

	@Autowired
	private RoleRepo roleDao;

	@Autowired
	private PasswordEncoder encoder;

	String userName = "Naruto";
	String email = "naruto@gmail.com";
	String password = "password";
	String mobileNo = "9988776655";

	@BeforeEach
	void setup() {
		Users user = new Users();
		user.setUserName(userName);
		user.setName(userName);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		user.setMobileNo(mobileNo);
		user.setStatus(UserStatus.DISABLED);
		Role testUser = new Role();
		testUser.setRoleName("tester");
		testUser.setRoleDescription("Dummy entry");
		roleDao.save(testUser);
		Set<Role> roles = new HashSet<>();
		roles.add(testUser);
		user.setRole(roles);
		System.out.println(user);
		dao.save(user);
	}

	@AfterEach
	void cleanup() {
		dao.deleteById(userName);
		roleDao.deleteById("tester");
	}

	@Test
	void testFindByEmail() {
		Users user = dao.findByEmail(email);
		assertNotNull(user);
		assertEquals(email, user.getEmail());
	}

	@Test
	void testFindByStatus() {
		List<Users> disabledUsers = dao.findByStatus(UserStatus.DISABLED);
		assertNotNull(disabledUsers);
		List<String> disabledUserList = disabledUsers.stream().map(user -> user.getEmail()).toList();
		List<String> list = new ArrayList<>();
		list.add(email);
		assertEquals(list.get(0), disabledUserList.get(0));
	}

}
