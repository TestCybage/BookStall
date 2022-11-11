package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.UserRole;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.ShortPasswordException;
import com.example.bookshop.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo dao;
	
	public List<Users> getAllUsers(){
		List<Users> users =  dao.findByRole(UserRole.USER);
		if(users.isEmpty())
			throw new EmptyRecordException(ErrorMessage.USER_LIST_EMPTY);
		return users;
	}
	
	public Users getUserByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	public Users signUp(Users user) {
		if(dao.findByEmail(user.getEmail())!=null)
			throw new AlreadyExistException(ErrorMessage.EMAIL_EXISTS);
		if(user.getPassword().length()<8)
			throw new ShortPasswordException(ErrorMessage.SHORT_PASSWORD);
		user.setStatus(UserStatus.ENABLED);
		user.setRole(UserRole.USER);
		return dao.save(user);
	}
	

}
