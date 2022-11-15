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
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.BookRepo;

@Service
public class BookService {

	@Autowired
	private BookRepo dao;

	@Autowired
	private AuthorService authorService;

	Logger logger = Logger.getLogger(BookService.class);

	public List<Book> getAllBooks() {
		List<Book> books = dao.findAll();
		if (books.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return books;
	}

	public Book getBookById(int id) {
		logger.info(id);
		return dao.findById(id).orElse(null);
	}

	public Book getBookByName(String name) {
		logger.info(name);
		Book book = dao.findByBookName(name.toUpperCase());
		if (book != null)
			return book;
		return null;
	}

	public Book addBook(Book newBook) {
		newBook.setBookName(newBook.getBookName().toUpperCase());
		logger.info(newBook);
		if (getBookById(newBook.getBookId()) != null)
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
		if (getBookByName(newBook.getBookName().toUpperCase()) != null)
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
		Author author = authorService.getByAuthorName(newBook.getAuthor().getAuthorName());
		if (author == null) {
			newBook.getAuthor().setAuthorName(newBook.getAuthor().getAuthorName());
			authorService.addAuthor(newBook.getAuthor());
		} else {
			newBook.setAuthor(author);
		}
		return dao.save(newBook);
	}

	public Book editPrice(int id, double price) {
		logger.info(id + " " + price);
		Book book = getBookById(id);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		book.setPrice(price);
		return dao.save(book);
	}
	
	public Book editQuantity(int id, int quantity) {
		logger.info(id+" "+quantity);
		if(quantity<0)
			throw new InvalidInputException(ErrorMessage.INVALID_INPUT_QUANTITY);
		Book book  = getBookById(id);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		book.setInStock(quantity);
		return dao.save(book);
	}

	public List<Book> deleteBook(int id) {
		Book book = getBookById(id);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		else {
			logger.info(book.getBookName());
			dao.delete(book);
			if(getBookByAuthorName(book.getAuthor().getAuthorName()).isEmpty())
				authorService.deleteAuthor(book.getAuthor().getAuthorId());
			return getAllBooks();
		}
	}

	public List<Book> getBookByAuthorName(String name) {
		Author author = authorService.getByAuthorName(name);
		if (author == null)
			throw new RecordNotFoundException(ErrorMessage.AUTHOR_NOT_FOUND);
		return dao.findByAuthor(author);

	}

	
}
