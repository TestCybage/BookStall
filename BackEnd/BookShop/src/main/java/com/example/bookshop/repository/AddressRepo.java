package com.example.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Users;

public interface AddressRepo extends JpaRepository<Address, Integer>{

	public List<Address> findByUser(Users user);
}
