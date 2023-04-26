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

@Service
public class AuthorService {

	@Autowired
	private AuthorRepo dao;
	
	@Autowired
	private BookService bookService;
	
	Logger logger = Logger.getLogger(AuthorService.class);

	public Author getAuthorById(int id) {
		logger.info(id);
		return dao.findById(id).orElse(null);
	}

	public Author addAuthor(Author author) {
		logger.info(author);
		if (getByAuthorName(author.getAuthorName()) == null) {
			author.setAuthorName(author.getAuthorName().toUpperCase()); 
			return dao.save(author);
		} 
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
	}

	public List<Author> getAllAuthor() {
		List<Author> authorList = dao.findAll();
		if (authorList.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return authorList;
	}
	
	public Author getByAuthorName(String name) {
		logger.info(name);
		Author author = dao.findByAuthorName(name.toUpperCase());
		if(author==null)
			return null;
		return author;
	}
	
	public boolean deleteAuthor(int id){
		Author author = getAuthorById(id);
		if(author == null)
			throw new RecordNotFoundException(ErrorMessage.AUTHOR_NOT_FOUND);
		List<Book> books = bookService.getBookByAuthorName(author.getAuthorName());
		for (Book book : books) {
			bookService.deleteBook(book.getBookId());
		}
		dao.delete(author);
		return !dao.existsById(id);
	}
	
	

}
