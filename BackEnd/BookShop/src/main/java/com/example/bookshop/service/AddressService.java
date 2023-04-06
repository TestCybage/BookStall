package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.AddressRepo;

@Service
public class AddressService {

	@Autowired
	private AddressRepo dao;

	@Autowired
	private UserService userService;

	public Address getById(int id) {
		return dao.findById(id).orElse(null);
	}

	public List<Address> getByUserName(String userName) {
		Users user = userService.getById(userName);
		if (user == null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		List<Address> addresses = dao.findByUser(user);
		if (addresses.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return addresses;
	}

	public Address addAddress(Address address, String userName) {
		Users user = userService.getById(userName);
		if (user == null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		address.setUser(user);
		return dao.save(address);
	}

	public Boolean deleteAddress(String userName,int id) {
		Address address = getById(id);
		if(address==null)
			throw new RecordNotFoundException(ErrorMessage.ADDRESS_NOT_FOUND);
		if(!address.getUser().getUserName().equals(userName))
			throw new InvalidInputException(ErrorMessage.INVALID_ADDRESS_ID);
		dao.deleteById(id);
		return !dao.existsById(id);
	}

	public Address updateAddress(Address address,String userName,int id) {
		Users user=userService.getById(userName);
		if (user == null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		address.setUser(user);
		address.setId(id);
		return dao.save(address);
	}
}
