package com.example.bookshop.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtResponseTest {
	
	JwtResponse entity1 = new JwtResponse(null, null);
	
	JwtResponse entity2 = new JwtResponse(null, null);
	
	Users user = new Users("test", null, null, null, null, null, null, null, null);
	
	String token = "abhjcvcv";
	
	JwtResponse response = new JwtResponse(user, token);

	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(entity1.hashCode(), response.hashCode());
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
        String expected = "JwtResponse";
        String actual = entity1.toString().substring(0, 11);
        assertEquals(expected, actual);
    }

}
