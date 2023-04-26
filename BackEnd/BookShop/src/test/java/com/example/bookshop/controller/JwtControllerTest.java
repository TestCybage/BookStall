package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.bookshop.dto.Credentials;
import com.example.bookshop.dto.UserDto;
import com.example.bookshop.entities.JwtRequest;
import com.example.bookshop.entities.JwtResponse;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.messages.Messages;
import com.example.bookshop.service.EmailService;
import com.example.bookshop.service.JwtService;
import com.example.bookshop.service.OTPService;
import com.example.bookshop.service.UserService;

@ExtendWith(MockitoExtension.class)
class JwtControllerTest {

	@Mock
	private JwtService service;
	
	@Mock
	private UserService userService;
	
	@Mock
	private OTPService otpService;
	
	@Mock
	private EmailService emailService;

	@InjectMocks
	private JwtController controller;

	private Role role = new Role("TestUser", "Test");

	private Set<Role> roles = new HashSet<>();

	private Users user;
	
	private Credentials cred = new Credentials("user1@example.com", "pass1234");
	
	private UserDto userDto;
	
	@BeforeEach
	void setup() {
		roles.add(role);
		user = new Users("user1@example.com", "user1", "user1@example.com", "pass1234", "9988556622",
				UserStatus.ENABLED, null, null, roles);
		userDto = UserDto.toDto(user);
		
	}

	@Test
	void testCreateJwtToken() throws LoginException {
		String token ="bhgadjggdhebyfetfe13254hag_jb#";
		JwtResponse jwtResponse = new JwtResponse(user, token);
		JwtRequest jwtRequest = new JwtRequest("user1@example.com", "pass1234");
		when(service.createJwtToken(jwtRequest)).thenReturn(jwtResponse);
		JwtResponse response = controller.createJwtToken(jwtRequest);
		assertNotNull(response);
		assertEquals(token, response.getJwtToken());
		assertEquals(user, response.getUser());
	}

	@Test
	void testSignUp() {
		when(userService.signUp(user)).thenReturn(user);
		ResponseEntity<UserDto> response = controller.signUp(userDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(userDto, response.getBody());
	}

	@Test
	void testDisableUser() {
		user.setStatus(UserStatus.DISABLED);
		when(userService.disableUser(user.getUserName())).thenReturn(user);
		ResponseEntity<UserDto> response = controller.disableUser(user.getUserName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(UserDto.toDto(user), response.getBody());
	}

	@Test
	void testSendOTP() throws MessagingException, NoSuchAlgorithmException {
		int otp=123456;
		String otpMessage = ""+otp+"";
		when(otpService.generateOTP(userDto.getEmail())).thenReturn(otp);
		when(emailService.sentOtpMessage(userDto.getEmail(), otp)).thenReturn(otpMessage);
		ResponseEntity<String> response = controller.sendOTP(userDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(otpMessage, response.getBody());
	}

	@Test
	void testValidateOtp() {
		int otp =123456;
		when(otpService.getOTP(user.getEmail())).thenReturn(otp);
		ResponseEntity<String> response = controller.validateOtp(otp, user.getEmail());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(Messages.VALID_OTP, response.getBody());
	}
	
	@Test
	void testValidateOtp_InvalidOtp1() {
		int otp =123456;
		when(otpService.getOTP(user.getEmail())).thenReturn(123465);
		ResponseEntity<String> response = controller.validateOtp(otp, user.getEmail());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(Messages.INVALID_OTP, response.getBody());
	}
	
	@Test
	void testValidateOtp_InvalidOtp2() {
		int otp =123456;
		when(otpService.getOTP(user.getEmail())).thenReturn(0);
		ResponseEntity<String> response = controller.validateOtp(otp, user.getEmail());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(Messages.INVALID_OTP, response.getBody());
	}
	
	@Test
	void testValidateOtp_InvalidOtp3() {
		int otp =-1;
		//when(otpService.getOTP(user.getEmail())).thenReturn(-1);
		ResponseEntity<String> response = controller.validateOtp(otp, user.getEmail());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(Messages.INVALID_OTP, response.getBody());
	}

	@Test
	void testForgotPassword() {
		when(userService.forgetPassword(cred)).thenReturn(user);
		ResponseEntity<UserDto> response = controller.forgotPassword(cred);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(UserDto.toDto(user), response.getBody());
	}

}
