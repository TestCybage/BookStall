package com.example.bookshop.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ErrorMessageTest {

	@Test
	void testErrorMessage() {
		ErrorMessage error = new ErrorMessage();
		assertNotNull(error);
	}

}
