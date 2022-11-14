package com.example.bookshop.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.ShortPasswordException;
import com.example.bookshop.repository.RoleRepo;
import com.example.bookshop.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo dao;
	
	@Autowired
	private RoleRepo roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger logger = Logger.getLogger(UserService.class);
	
	
	
	public Users getUserByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	public Users signUp(Users user) {
		if(dao.findById(user.getUserName()).orElse(null)!=null)
			throw new AlreadyExistException(ErrorMessage.USERNAME_EXISTS);
		if(dao.findByEmail(user.getEmail())!=null)
			throw new AlreadyExistException(ErrorMessage.EMAIL_EXISTS);
		if(user.getPassword().length()<8)
			throw new ShortPasswordException(ErrorMessage.SHORT_PASSWORD);
		Role role = roleDao.findById("USER").get();
		logger.info(role);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setStatus(UserStatus.ENABLED);
		user.setPassword(getEncodedPassword(user.getPassword()));
		return dao.save(user);
	}
	
	public void initRolesAndUsers() {
		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		adminRole.setRoleDescription("ADMIN role");
		roleDao.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("USER");
		userRole.setRoleDescription("Default role for the user");
		roleDao.save(userRole);
		
		Users adminUser = new Users();
		adminUser.setUserName("Admin");
		adminUser.setName("Admin");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setMobileNo("1234567980");
		adminUser.setPassword(getEncodedPassword("admin@pass"));
		Set<Role>adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		dao.save(adminUser);
		
//		Users user = new Users();
//		user.setUserName("aashay");
//		user.setName("Aashay Kadu");
//		user.setEmail("aashay@gmail.com");
//		user.setMobileNo("1234567980");
//		user.setPassword(getEncodedPassword("aashay@pass"));
//		user.setStatus(UserStatus.ENABLED);
//		Set<Role>userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		dao.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	

}
