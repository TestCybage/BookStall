package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.bookshop.entities.Role;

class RoleDtoTest {
	
	private Role role;
	
	private RoleDto roleDto;
	
	public RoleDtoTest() {
		role = new Role("Test", "test");
		roleDto=new RoleDto("Test", "test");
	}

	@Test
	void testToDto() {
		RoleDto dto = RoleDto.toDto(role);
		assertEquals(dto, roleDto);
	}

	@Test
	void testToEntity() {
		Role entity = RoleDto.toEntity(roleDto);
		assertEquals(entity, role);
	}

}
