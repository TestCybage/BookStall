package com.example.bookshop.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.CartRepo;

@Service
public class CartService {

	@Autowired
	private CartRepo dao;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	Logger log = Logger.getLogger(CartService.class);

	public Cart getCartByUserName(String userName) {
		Users user = userService.getById(userName);
		if (user == null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		return dao.findByUser(user);
	}

	public Cart addToCart(int bookId, int quantity, String userName) {
		log.info(bookId + " " + quantity + " " + userName);
		Cart cart = getCartByUserName(userName);
		if (cart == null) {
			cart = createCart(userName);
		}
		Map<String, Integer> books = cart.getBooks();
		Set<String> bookNames = books.keySet();
		Book book = bookService.getBookById(bookId);
		if (book == null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		if (books.containsKey(book.getBookName()))
			books.replace(book.getBookName(), books.get(book.getBookName()) + quantity);
		else
			books.putIfAbsent(book.getBookName(), quantity);
		double amount = 0;
		for (String name : bookNames) {
			amount = amount + bookService.getBookByName(name).getPrice() * books.get(name);
		}

		log.info(amount);

		cart.setBooks(books);
		cart.setAmount(amount);
		log.info(cart.getAmount());
		return dao.save(cart);
	}

	public Cart emptyCart(String userName) {
		Cart cart = getCartByUserName(userName);
		if(cart==null)
			throw new RecordNotFoundException(ErrorMessage.EMPTY_CART);
		cart.setBooks(new HashMap<>());
		cart.setAmount(0);
		return dao.save(cart);
	}
	
	public Cart createCart(String userName) {
		if(getCartByUserName(userName)!=null)
			throw new AlreadyExistException(ErrorMessage.ALREADY_EXIST);
		Users user = userService.getById(userName);
		Cart cart = new Cart();
		cart.setBooks(new HashMap<>());
		cart.setAmount(0);
		cart.setUser(user);
		return dao.save(cart);
	}
}
