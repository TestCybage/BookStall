package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.bookshop.dto.RoleDto;
import com.example.bookshop.entities.Role;
import com.example.bookshop.service.RoleService;
@ExtendWith(MockitoExtension.class)
class RoleControllerTest {
	
	@Mock
	private RoleService service;
	
	@InjectMocks
	private RoleController controller;
	
	Role role = new Role("Tester", "Testing");

	@Test
	void testCreateNewRole() {
		when(service.createNewRole(role)).thenReturn(role);
		RoleDto response = controller.createNewRole(RoleDto.toDto(role));
		assertNotNull(response);
		assertEquals(RoleDto.toDto(role), response);
	}

}
