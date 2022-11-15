package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookshop.entities.Role;
import com.example.bookshop.repository.RoleRepo;

@SpringBootTest
class RoleServiceTest {
	
	@MockBean
	private RoleRepo dao;
	
	@Autowired
	private RoleService service;

	@Test
	void testCreateNewRole() {
		Role role = new Role("Tester","Testing Role Service");
		service.createNewRole(role);
		
		Mockito.when(dao.save(role)).thenReturn(role);
		assertThat(service.createNewRole(role)).isEqualTo(role);
	}

}
