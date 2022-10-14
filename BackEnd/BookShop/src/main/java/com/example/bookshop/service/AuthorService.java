package com.example.bookshop.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.AuthorRepo;
import com.example.bookshop.repository.BookRepo;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepo dao;
	
	@Autowired
	private BookRepo bookDao;
	
	Logger logger = Logger.getLogger(AuthorService.class);

	public Author getAuthorById(int id) {
		logger.info(id);
		return dao.findById(id).orElse(null);
	}

	public Author addAuthor(Author author) {
		logger.info(author);
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
		logger.info(name);
		return dao.findByAuthorName(name.toUpperCase());
	}
	
	public List<Author> deleteAuthor(int id){
		Author author = getAuthorById(id);
		if(author == null)
			throw new RecordNotFoundException(ErrorMessage.AUTHOR_NOT_FOUND);
		List<Book> books = bookDao.findByAuthor(author);
		for (Book book : books) {
			bookDao.delete(book);
		}
		dao.delete(author);
		return getAllAuthor();
	}
	
	public void deleteAllAuthors() {
		dao.deleteAll();
	}

}
