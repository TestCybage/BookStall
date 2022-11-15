package com.example.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookshop.dto.RoleDto;
import com.example.bookshop.entities.Role;
import com.example.bookshop.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@PostMapping("/createNewRole")
	public Role createNewRole(@RequestBody RoleDto roleDto) {
		return service.createNewRole(RoleDto.toEntity(roleDto));
	}
}
