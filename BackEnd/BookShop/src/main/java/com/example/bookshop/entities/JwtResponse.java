package com.example.bookshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

	private Users user;
	
	private String jwtToken;
}
