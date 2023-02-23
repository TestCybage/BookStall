package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.bookshop.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService service;

	@InjectMocks
	private UserController controller;

	@Test
	void testInitRolesAndUsers() {
		controller.initRolesAndUsers();
		verify(service).initRolesAndUsers();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testForUser() {
		assertEquals("This URL is only accessible to USER", controller.forUser());
	}

}
