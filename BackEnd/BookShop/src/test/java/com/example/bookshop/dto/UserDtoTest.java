package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

class UserDtoTest {
	
	private Role role  = new Role("tester", "test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user;
	
	private UserDto userDto;
	
	public UserDtoTest() {
		roles.add(role);
		user = new Users("Tester", "test", "test@test.com", "test@pass", "8899775588", UserStatus.ENABLED, null, null, roles);
		userDto = new UserDto("Tester", "test", "test@test.com", "test@pass", "8899775588", UserStatus.ENABLED, null, null, roles);
	}

	@Test
	void testToDtoUsers() {
		UserDto dto = UserDto.toDto(user);
		assertEquals(userDto, dto);
	}

	@Test
	void testToEntityUserDto() {
		Users entity = UserDto.toEntity(userDto);
		assertEquals(user, entity);
	}

	@Test
	void testToDtoListOfUsers() {
		List<Users> entityList = new ArrayList<>();
		entityList.add(user);
		List<UserDto> dtoList = UserDto.toDto(entityList);
		assertEquals(1, dtoList.size());
		assertEquals(userDto, dtoList.get(0));
	}

	@Test
	void testToEntityListOfUserDto() {
		List<UserDto> dtoList = new ArrayList<>();
		dtoList.add(userDto);
		List<Users> entityList = UserDto.toEntity(dtoList);
		assertEquals(1, entityList.size());
		assertEquals(user, entityList.get(0));
	}

}
