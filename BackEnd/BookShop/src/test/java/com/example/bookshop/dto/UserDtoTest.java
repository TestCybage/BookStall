package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

class UserDtoTest {
	
	private Role role  = new Role("tester", "test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user;
	
	private UserDto userDto;
	
	private UserDto dto1;
	
	private UserDto dto2;
	
	public UserDtoTest() {
		roles.add(role);
		user = new Users("Tester", "test", "test@test.com", "test@pass", "8899775588", UserStatus.ENABLED, null, null, roles);
		userDto = new UserDto("Tester", "test", "test@test.com", "test@pass", "8899775588", UserStatus.ENABLED, null, null, roles);
		dto1 = new UserDto();
		dto2 = new UserDto();
	}
	
	 @DisplayName("Test hashCode method")
	    void testHashCode() {
	        assertEquals(dto1.hashCode(), dto2.hashCode());
	    }
		
		@Test
	    @DisplayName("Test hashCode method")
	    void testHashCodeNotEquals() {
	        assertNotEquals(dto1.hashCode(), userDto.hashCode());
	    }
		
		@Test
	    @DisplayName("Test equals method with equal objects")
	    void testEqualsWithEqualObjects() {
	        assertTrue(dto1.equals(dto2));
	    }

	    @Test
	    @DisplayName("Test equals method with non-equal objects")
	    void testEqualsWithNonEqualObjects() {
	        assertFalse(dto1.equals(null));
	        assertFalse(dto1.equals(userDto));
	        assertFalse(dto1.equals(new Object()));
	    }
	    
	    @Test
	    @DisplayName("Test toString method")
	    void testToString() {
	        String expected = "UserDto";
	        String actual = dto1.toString().substring(0, 7);
	        assertEquals(expected, actual);
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
