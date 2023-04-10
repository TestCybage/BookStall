package com.example.bookshop.controller;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.Credentials;
import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.JwtRequest;
import com.example.bookshop.entities.JwtResponse;
import com.example.bookshop.messages.Messages;
import com.example.bookshop.service.EmailService;
import com.example.bookshop.service.JwtService;
import com.example.bookshop.service.OTPService;
import com.example.bookshop.service.UserService;

@RestController
@CrossOrigin("*")
public class JwtController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService service;

	@Autowired
	private OTPService otpService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/authenticate")
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws LoginException {
		return jwtService.createJwtToken(jwtRequest);

	}

	@PostMapping("/signUp")
	public ResponseEntity<UserDto> signUp(@RequestBody UserDto dto) {
		return new ResponseEntity<>(UserDto.toDto(service.signUp(UserDto.toEntity(dto))), HttpStatus.CREATED);
	}

	@PutMapping("/disable/{userName}")
	public ResponseEntity<UserDto> disableUser(String userName) {
		return new ResponseEntity<>(UserDto.toDto(service.disableUser(userName)), HttpStatus.OK);
	}

	@PostMapping("/sendOTP")
	public ResponseEntity<String> sendOTP(@RequestBody UserDto dto) throws MessagingException {
		String email = dto.getEmail();
		int otp = otpService.generateOTP(email);
		return new ResponseEntity<>(emailService.sentOtpMessage(email, otp), HttpStatus.OK);
	}

	@GetMapping("/validate/{otp}/{email}")
	public ResponseEntity<String> validateOtp(@PathVariable int otp, @PathVariable String email) {
		if (otp >= 0) {
			int serverOTP = otpService.getOTP(email);
			if (serverOTP > 0) {
				if (otp == serverOTP) {
					otpService.clearOTP(email);
					return new ResponseEntity<>(Messages.VALID_OTP, HttpStatus.OK);
				} else
					return new ResponseEntity<>(Messages.INVALID_OTP, HttpStatus.OK);
			} else
				return new ResponseEntity<>(Messages.INVALID_OTP, HttpStatus.OK);
		} else
			return new ResponseEntity<>(Messages.INVALID_OTP, HttpStatus.OK);
	}
	
	@PutMapping("/forgotPassword")
	public ResponseEntity<UserDto> forgotPassword(@RequestBody Credentials cred){
		return new ResponseEntity<>(UserDto.toDto(service.forgetPassword(cred)), HttpStatus.OK);
	}
}
