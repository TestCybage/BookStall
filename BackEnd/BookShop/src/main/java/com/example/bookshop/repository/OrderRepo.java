package com.example.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Users;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {
	
	public List<Orders> findByUser(Users user);
	
	public List<Orders> findAllByOrderByOrderIdDesc();
}
