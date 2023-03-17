package com.example.bookshop.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Users;
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
		log.info(bookId+" "+quantity+" "+userName);
		if(getCartByUserName(userName)==null) {
			Cart cart1 = new Cart();
				cart1.setUser(userService.getById(userName));
				cart1.setBooks(new ArrayList<>());
				cart1.setAmount(0);
				dao.save(cart1);
		}
		Cart cart = getCartByUserName(userName);
		List<Book> books = cart.getBooks();
		Book book = bookService.getBookById(bookId);
		if(book==null)
			throw new RecordNotFoundException(ErrorMessage.BOOK_NOT_FOUND);
		double amount = cart.getAmount();
		log.info(amount);
		for (int i = 0; i < quantity; i++) {
			books.add(book);
			amount = amount + book.getPrice();
		}
		cart.setBooks(books);
		cart.setAmount(amount);
		log.info(cart.getAmount());
		log.info(cart.getBooks().get(0).getBookName());
		return cart;
	}
	
	public Cart emptyCart(String userName) {
		Cart cart = getCartByUserName(userName);
		cart.setAmount(0);
		cart.setBooks(null);
		return dao.save(cart);
	}
}
