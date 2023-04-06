package com.example.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.AddressDto;
import com.example.bookshop.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
@PreAuthorize("hasRole('USER')")
public class AddressController {
	
	@Autowired
	private AddressService service;

	@GetMapping("/getByUserName/{userName}")
	public ResponseEntity<List<AddressDto>> getByUserName(@PathVariable String userName){
		return new ResponseEntity<>(AddressDto.toDto(service.getByUserName(userName)), HttpStatus.OK);
	}
	
	@PostMapping("/addAddress/{userName}")
	public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto dto,@PathVariable String userName){
		return new ResponseEntity<>(AddressDto.toDto(service.addAddress(AddressDto.toEntity(dto), userName)),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteAddress/{userName}/{id}")
	public ResponseEntity<Boolean> deleteAddress(@PathVariable String userName, @PathVariable int id){
		return new ResponseEntity<>(service.deleteAddress(userName,id), HttpStatus.OK);
	}
	
	@PutMapping("/updateAddress/{userName}/{id}")
	public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto dto,@PathVariable String userName,@PathVariable int id){
		return new ResponseEntity<>(AddressDto.toDto(service.updateAddress(AddressDto.toEntity(dto),userName,id)),HttpStatus.ACCEPTED);
	}
	
}
