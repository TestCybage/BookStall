package com.example.bookshop.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtRequestTest {
	
	JwtRequest entity1 = new JwtRequest();
	
	JwtRequest entity2 = new JwtRequest();
	
	JwtRequest request = new JwtRequest("userName", "code");

	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(entity1.hashCode(), request.hashCode());
    }
	
	@Test
    @DisplayName("Test equals method with equal objects")
    void testEqualsWithEqualObjects() {
        assertTrue(entity1.equals(entity2));
    }

    @Test
    @DisplayName("Test equals method with non-equal objects")
    void testEqualsWithNonEqualObjects() {
        assertFalse(entity1.equals(null));
        assertFalse(entity1.equals(new Object()));
    }
    
    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "JwtRequest";
        String actual = entity1.toString().substring(0, 10);
        assertEquals(expected, actual);
    }

}
