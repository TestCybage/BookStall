package com.example.bookshop.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.OrdersDto;
import com.example.bookshop.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	private OrderService service;
	
	Logger log  = Logger.getLogger(OrderController.class);
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllOrders")
	public ResponseEntity<List<OrdersDto>> getAllOrders(){
		return new ResponseEntity<>(OrdersDto.toDto(service.getAllOrders()), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/getByUserName/{userName}")
	public ResponseEntity<List<OrdersDto>> getOrderByUserName(@PathVariable String userName){
		return new ResponseEntity<>(OrdersDto.toDto(service.getOrderByUserName(userName)), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/addOrder/{userName}/{addressId}")
	public ResponseEntity<OrdersDto> addOrder(@PathVariable String userName,@PathVariable int addressId){
		return new ResponseEntity<>(OrdersDto.toDto(service.addOrder(userName,addressId)), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PatchMapping("/cancelOrder/{id}")
	public ResponseEntity<OrdersDto> cancelOrder(@PathVariable int id){
		return new ResponseEntity<>(OrdersDto.toDto(service.cancelOrder(id)), HttpStatus.ACCEPTED);
	}
}
