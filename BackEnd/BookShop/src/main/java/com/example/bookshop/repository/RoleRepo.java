package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookshop.entities.Role;

public interface RoleRepo extends JpaRepository<Role, String>{

}
