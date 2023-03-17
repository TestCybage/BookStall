package com.example.bookshop.service;

import java.util.ArrayList;
import java.util.List;

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

	public Cart getCartByUserName(String userName) {
		Users user = userService.getById(userName);
		if (user == null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		return dao.findByUser(user);
	}

	public Cart addToCart(Book book, int quantity, String userName) {
		if(getCartByUserName(userName)==null) {
			Cart cart1 = new Cart();
				cart1.setUser(userService.getById(userName));
				cart1.setBooks(new ArrayList<>());
				cart1.setAmount(0);
				dao.save(cart1);
		}
		Cart cart = getCartByUserName(userName);
		List<Book> books = cart.getBooks();
		double amount = cart.getAmount();
		for (int i = 0; i < quantity; i++) {
			books.add(book);
			amount = amount + book.getPrice();
		}
		cart.setBooks(books);
		cart.setAmount(amount);
		return cart;
	}
	
	public Cart emptyCart(String userName) {
		Cart cart = getCartByUserName(userName);
		cart.setAmount(0);
		cart.setBooks(null);
		return dao.save(cart);
	}
}
