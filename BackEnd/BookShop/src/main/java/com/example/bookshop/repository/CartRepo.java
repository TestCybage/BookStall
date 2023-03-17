package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Users;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

	public Cart findByUser(Users user);
}
