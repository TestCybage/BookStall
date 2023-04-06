package com.example.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.RoleDto;
import com.example.bookshop.service.RoleService;

@RestController
@CrossOrigin("*")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@PostMapping("/createNewRole")
	public RoleDto createNewRole(@RequestBody RoleDto roleDto) {
		return RoleDto.toDto(service.createNewRole(RoleDto.toEntity(roleDto))); 
	}
}
