package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.dto.OrdersDto;
import com.example.bookshop.entities.OrderedBook;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.repository.OrderRepo;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepo dao;
	
	

}
