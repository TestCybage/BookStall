package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {
	

}
