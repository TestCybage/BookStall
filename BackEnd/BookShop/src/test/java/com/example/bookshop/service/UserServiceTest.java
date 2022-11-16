package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.repository.RoleRepo;
import com.example.bookshop.repository.UserRepo;

@SpringBootTest
class UserServiceTest {

	Users user = new Users();
	String id = "aashay";
	String name = "Aashay Kadu";
	String email = "aashay@test.com";
	String pass = "aashay@test";
	String mobile="1234567890";
	
	Role userRole = new Role();
	Role adminRole = new Role();
	Set<Role> roles = new HashSet<>();
	

	public UserServiceTest() {
		
		user.setUserName(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(pass);
		user.setMobileNo(mobile);
		user.setStatus(UserStatus.ENABLED);
		
		userRole.setRoleName("USER");
		userRole.setRoleDescription("Default role for the user");
		
		adminRole.setRoleName("ADMIN");
		adminRole.setRoleDescription("ADMIN role");

		
		roles.add(userRole);
		user.setRole(roles);
	}

	@Autowired
	private UserService service;

	@MockBean
	private UserRepo dao;
	
	@MockBean
	private RoleRepo roleDao;
	
	@MockBean
	private PasswordEncoder encoder;

	@Test
	void testGetUserByEmail() {
		Mockito.when(dao.findByEmail(email)).thenReturn(user);
		assertThat(service.getUserByEmail(email)).isEqualTo(user);
	}

	@Test
	void testSignUp() {
		
		Mockito.when(dao.findById(id)).thenReturn(Optional.ofNullable(null));
		Mockito.when(dao.findByEmail(email)).thenReturn(null);
		
		Optional<Role> role = Optional.ofNullable(userRole);
		Mockito.when(roleDao.findById("USER")).thenReturn(role);
		Mockito.when(encoder.encode(pass)).thenReturn(pass);
		Mockito.when(dao.save(user)).thenReturn(user);
		assertThat(service.signUp(user)).isEqualTo(user);
	}

	@Test
	void testGetEncodedPassword() {
		Mockito.when(encoder.encode(pass)).thenReturn(pass);
		assertThat(service.getEncodedPassword(pass)).isEqualTo(pass);
	}

	@Test
	void testUnblockUser() {
		user.setStatus(UserStatus.DISABLED);
		Mockito.when(dao.findByEmail(email)).thenReturn(user);
		Mockito.when(dao.save(user)).thenReturn(user);
		assertThat(service.unblockUser(email)).isEqualTo(user);
	}

	@Test
	void testGetById() {
		Optional<Users> user1 = Optional.ofNullable(user);
		Mockito.when(dao.findById(id)).thenReturn(user1);
		assertThat(service.getById(id)).isEqualTo(user);
	}

	@Test
	void testDisableUser() {
		Mockito.when(dao.findById(id)).thenReturn(Optional.ofNullable(user));
		user.setStatus(UserStatus.DISABLED);
		Mockito.when(dao.save(user)).thenReturn(user);
		assertThat(service.disableUser(id)).isEqualTo(user);
	}

	@Test
	void testGetDisabledUsers() {
		user.setStatus(UserStatus.DISABLED);
		List<Users> users = new ArrayList<>();
		users.add(user);
		Mockito.when(dao.findByStatus(UserStatus.DISABLED)).thenReturn(users);
		assertThat(service.getDisabledUsers()).isEqualTo(users);
	}

}
