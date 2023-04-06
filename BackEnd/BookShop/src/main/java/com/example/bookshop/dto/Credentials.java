package com.example.bookshop.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
	
	@Email
	@NotNull
	private String userEmail;
	
	@NotNull
	private String newPassword;
	
}
