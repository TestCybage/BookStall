package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.bookshop.entities.Role;

class RoleDtoTest {
	
	private Role role;
	
	private RoleDto roleDto;
	
	private RoleDto dto1;
	
	private RoleDto dto2;
	
	public RoleDtoTest() {
		role = new Role("Test", "test");
		roleDto=new RoleDto("Test", "test");
		
		dto1 = new RoleDto();
		
		dto2 = new RoleDto();
	}
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(dto1.hashCode(), roleDto.hashCode());
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
        assertFalse(dto1.equals(roleDto));
        assertFalse(dto1.equals(new Object()));
    }
    
    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "RoleDto";
        String actual = dto1.toString().substring(0, 7);
        assertEquals(expected, actual);
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
