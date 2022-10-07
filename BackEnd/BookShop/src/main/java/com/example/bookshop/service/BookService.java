package com.example.bookshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Book;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.BookRepo;

@Service
public class BookService {

	@Autowired
	private BookRepo dao;

	public List<Book> getAllBooks() {
		List<Book> books = dao.findAll();
		if (books.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return books;
	}

	public Book getBookById(int id) {
		return dao.findById(id).orElse(null);
	}

	public List<Book> getBookByName(String name) {

		List<Book> books = dao.findByBookName(name);
		if (books.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY);
		return books;
	}

	public Book addBook(Book newBook) {
		if (getBookById(newBook.getBookId()) != null)
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
		return dao.save(newBook);
	}

	public Book editPrice(int id, double price) {
		Book book = getBookById(id);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		book.setPrice(price);
		return dao.save(book);
	}

	public List<Book> deleteBook(int id) {
		Book book = getBookById(id);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		else {
			dao.delete(book);
			return getAllBooks();
		}
	}

}
