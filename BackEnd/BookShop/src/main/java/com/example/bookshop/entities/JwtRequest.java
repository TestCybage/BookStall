package com.example.bookshop.entities;

import lombok.Data;

@Data
public class JwtRequest {
	
	private String userName;
	
	private String userPassword;

}
