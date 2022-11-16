package com.example.bookshop.exception;

public class AlreadyEnabledException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyEnabledException(String message) {
		super(message);
	}
}
