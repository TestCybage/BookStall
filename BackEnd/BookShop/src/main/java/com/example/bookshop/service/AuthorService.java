package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Author;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.repository.AuthorRepo;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepo dao;

	public Author getAuthorById(int id) {
		return dao.findById(id).orElse(null);
	}

	public Author addAuthor(Author author) {
		if (getAuthorById(author.getAuthorId()) != null)
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
		if (getByAuthorName(author.getAuthorName()) == null) {
			author.setAuthorName(author.getAuthorName().toUpperCase()); 
			return dao.save(author);
		} else
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
	}

	public List<Author> getAllAuthor() {
		if (dao.findAll().isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return dao.findAll();
	}
	
	public Author getByAuthorName(String name) {
		return dao.findByAuthorName(name.toUpperCase());
	}

}
