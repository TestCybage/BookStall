package com.example.bookshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<String> notFound(RecordNotFoundException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmptyRecordException.class)
	public ResponseEntity<String> emptyRecords(EmptyRecordException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<String> alreadyExist(AlreadyExistException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> invalidInputQuantity(InvalidInputException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(ShortPasswordException.class)
	public ResponseEntity<String> shortPasswordException(ShortPasswordException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(AlreadyEnabledException.class)
	public ResponseEntity<String> alreadyEnabledException(AlreadyEnabledException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

}