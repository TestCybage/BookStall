package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.service.UserService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private AdminController adminController;
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	void testForAdmin() {
		assertEquals("This URL is only accessible to ADMIN", adminController.forAdmin());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testUnblockUser() {
		Role role = new Role("TestUser", "Test");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		Users user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, roles);
		when(userService.unblockUser("user1@example.com")).thenReturn(user);

		ResponseEntity<UserDto> response = adminController.unblockUser("user1@example.com");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("user1@example.com", response.getBody().getEmail());
		assertEquals(UserStatus.ENABLED, response.getBody().getStatus());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetDisabledUsers() {
		List<Users> disabledUsers = new ArrayList<>();
		Role role = new Role("TestUser", "Test");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		disabledUsers.add(new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.DISABLED, null, roles));
		disabledUsers.add(new Users("user2@example.com", "user2", "user2@example.com", "pass1234", "9988556611",
				UserStatus.DISABLED, null, roles));

		when(userService.getDisabledUsers()).thenReturn(disabledUsers);

		ResponseEntity<List<UserDto>> response = adminController.getDisabledUsers();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		assertEquals("user1@example.com", response.getBody().get(0).getEmail());
		assertEquals(UserStatus.DISABLED, response.getBody().get(0).getStatus());
		assertEquals("user2@example.com", response.getBody().get(1).getEmail());
		assertEquals(UserStatus.DISABLED, response.getBody().get(1).getStatus());
	}
}
