package com.example.bookshop.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

	Address entity1 = new Address();
	
	Address entity2 = new Address();
	
	Address address = new Address(100, null, null, 0, null);
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(entity1.hashCode(), address.hashCode());
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
        String expected = "Address";
        String actual = entity1.toString().substring(0, 7);
        assertEquals(expected, actual);
    }

}
