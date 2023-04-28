package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.LoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.bookshop.entities.JwtRequest;
import com.example.bookshop.entities.JwtResponse;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.repository.UserRepo;
import com.example.bookshop.util.JwtUtil;

class JwtServiceTest {

	@Mock
	private UserRepo dao;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private JwtService jwtService;

	String testUser = "testuser";
	String testPass = "testpassword";

	@SuppressWarnings("deprecation")
	public JwtServiceTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateJwtToken() throws LoginException {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUserName(testUser);
		jwtRequest.setUserPassword(testPass);

		Users user = new Users();
		user.setUserName(testUser);
		user.setPassword(testPass);
		user.setStatus(UserStatus.ENABLED);

		Role role = new Role();
		role.setRoleName("USER");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		when(dao.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(user));
		when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("testtoken");

		JwtResponse result = jwtService.createJwtToken(jwtRequest);

		Assertions.assertEquals(user, result.getUser());
		Assertions.assertEquals("testtoken", result.getJwtToken());
	}

	@Test
	void testLoadUserByUsername() {
		Users user = new Users();
		user.setUserName(testUser);
		user.setPassword(testPass);
		user.setStatus(UserStatus.ENABLED);

		Role role = new Role();
		role.setRoleName("USER");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);

		when(dao.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(user));

		UserDetails userDetails = jwtService.loadUserByUsername(testUser);

		Assertions.assertEquals(user.getUserName(), userDetails.getUsername());
		Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
		Assertions.assertEquals(jwtService.getAuthorities(user), userDetails.getAuthorities());
	}

	@Test
	void testLoadUserByUsernameWithDisabledUser() {
		Users user = new Users();
		user.setUserName(testUser);
		user.setPassword(testPass);
		user.setStatus(UserStatus.DISABLED);

		Role role = new Role();
		role.setRoleName("USER");

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		when(dao.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(user));
		assertThrows(DisabledException.class, () -> jwtService.loadUserByUsername(testUser));
	}

	@Test
	void testLoadUserByUsernameWithInvalidUsername() {
		when(dao.findById(Mockito.anyString())).thenReturn(java.util.Optional.empty());

		Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtService.loadUserByUsername("invaliduser"));
	}

	@SuppressWarnings("unused")
	private Set<SimpleGrantedAuthority> getAuthorities(Users user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		user.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

		return authorities;
	}

	@Test
	void testAuthenticationDisabledUser() {
		Role role = new Role("tester", "test");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		@SuppressWarnings("unused")
		Users user = new Users(testUser, testUser, "test@test.com", testPass, "8877558855", UserStatus.DISABLED, null,
				null, roles);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new DisabledException("USER is Disabled Please Contact Admin"));
		assertThrows(LoginException.class, () -> jwtService.authenticate(testUser, testPass),
				"Authenticate should throw LoginException with error message 'USER is Disabled Please Contact Admin'");
	}

	@Test
	void testAuthenticationBadCredentials() {
		Role role = new Role("tester", "test");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		@SuppressWarnings("unused")
		Users user = new Users(testUser, testUser, "test@test.com", testPass, "8877558855", UserStatus.DISABLED, null,
				null, roles);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		assertThrows(LoginException.class, () -> jwtService.authenticate(testUser, "abc"),
				"Authenticate should throw LoginException with error message 'Invalid username or password'");

	}

}
