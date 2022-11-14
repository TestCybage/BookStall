package com.example.bookshop.service;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.JwtRequest;
import com.example.bookshop.entities.JwtResponse;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.repository.UserRepo;
import com.example.bookshop.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserRepo dao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		String newGeneratedToken = jwtUtil.generateToke(userDetails);
		Users user =  dao.findById(userName).orElse(null);
		return new JwtResponse(user, newGeneratedToken);

	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Users user = dao.findById(userName).orElse(null);

		if (user != null) {
			return new User(user.getUserName(), user.getPassword(), getAuthorities(user));
		} else {
			throw new UsernameNotFoundException(ErrorMessage.INVALID_USERNAME);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set getAuthorities(Users user) {
		Set authorities = new HashSet<>();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new LoginException(ErrorMessage.USER_DISABLED);
		} catch (BadCredentialsException e) {
			throw new LoginException(ErrorMessage.BAD_CREDENTIALS);
		}
	}

}
