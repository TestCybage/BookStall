package com.example.bookshop.exception;

public class ShortPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShortPasswordException(String message) {
		super(message);
	}

}
