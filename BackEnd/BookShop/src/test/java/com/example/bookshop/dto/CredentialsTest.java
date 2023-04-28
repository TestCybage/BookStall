package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CredentialsTest {

	Credentials dto1 = new Credentials();

	Credentials dto2 = new Credentials();

	Credentials cred = new Credentials("abc@g.com", "jhd");

	@Test
	@DisplayName("Test hashCode method")
	void testHashCode() {
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	@DisplayName("Test hashCode method")
	void testHashCodeNotEquals() {
		assertNotEquals(dto1.hashCode(), cred.hashCode());
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
		assertFalse(dto1.equals(cred));
		assertFalse(dto1.equals(new Object()));
	}

	@Test
	@DisplayName("Test toString method")
	void testToString() {
		String expected = "Credentials";
		String actual = dto1.toString().substring(0, 11);
		assertEquals(expected, actual);
	}

}
