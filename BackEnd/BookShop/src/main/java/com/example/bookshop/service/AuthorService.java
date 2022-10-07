package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Author;
import com.example.bookshop.repository.AuthorRepo;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepo dao;
	
	public Author addAuthor(Author author) {
		return dao.save(author);
	}
	
	public List<Author> getAllAuthor(){
		return dao.findAll();
	}
	

}
