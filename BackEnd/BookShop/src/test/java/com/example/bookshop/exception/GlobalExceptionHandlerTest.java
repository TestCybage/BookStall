package com.example.bookshop.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

	@Test
	void testNotFound() {
		RecordNotFoundException exception = new RecordNotFoundException("Record not found");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.notFound(exception);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Record not found", response.getBody());
	}

	@Test
	void testEmptyRecords() {
		EmptyRecordException exception = new EmptyRecordException("Records Empty");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.emptyRecords(exception);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Records Empty", response.getBody());
	}

	@Test
	void testAlreadyExist() {
		AlreadyExistException exception = new AlreadyExistException("Record Already Exists");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.alreadyExist(exception);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("Record Already Exists", response.getBody());
	}

	@Test
	void testInvalidInputQuantity() {
		InvalidInputException exception = new InvalidInputException("Invalid Input");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.invalidInputQuantity(exception);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals("Invalid Input", response.getBody());
	}

	@Test
	void testShortPasswordException() {
		ShortPasswordException exception = new ShortPasswordException("Short Password");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.shortPasswordException(exception);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals("Short Password", response.getBody());
	}

	@Test
	void testAlreadyEnabledException() {
		AlreadyEnabledException exception = new AlreadyEnabledException("User is Enabled");
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<String> response = handler.alreadyEnabledException(exception);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("User is Enabled", response.getBody());
	}

}
