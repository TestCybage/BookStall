package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Author;
@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer>{
	
	public Author findByAuthorName(String name);
}
