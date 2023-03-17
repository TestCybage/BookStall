package com.example.bookshop.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.repository.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo dao;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BookService bookService;

	Logger logger = Logger.getLogger(OrderService.class);

	public List<Orders> getOrderByUserName(String userName) {
		Users user = userService.getById(userName);
		logger.info(user);
		return dao.findByUser(user);
	}

	public Orders getById(int id) {
		logger.info(id);
		return dao.findById(id).orElse(null);
	}

	public Orders addOrder(String userName) {
		logger.info(userName);
		Cart cart = cartService.getCartByUserName(userName);
		
		Orders order = new Orders();
		order.setOrderedBooks(cart.getBooks());
		order.setAmount(cart.getAmount());
		order.setUser(cart.getUser());
		order.setStatus(OrderStatus.COMPLETED);
		for(Book book:cart.getBooks()) {
			bookService.editQuantity(book.getBookId(), book.getInStock()-1);
			bookService.changeCopiesSold(book.getBookId(), 1);
		}
		cartService.emptyCart(cart.getUser().getUserName());
		logger.info(cart);
		sendInvoice(order);
		return dao.save(order);
	}

	public Orders cancelOrder(int id) {
		logger.info(id);
		Orders order = getById(id);
		if (order == null)
			throw new RecordNotFoundException(ErrorMessage.ORDER_NOT_FOUND);
		order.setStatus(OrderStatus.CANCELLED);
		return dao.save(order);
	}

	public void sendInvoice(Orders order) {
		StringBuilder invoiceTable = new StringBuilder();
//		invoiceTable.append(
//				"</tr>\r\n"
//				+"</tr>\r\n"
//				+"		<td>"+order.getOrderId()+"</td>\r\n"
//				+"		<td>"+order.getUser().getName()+"</td>\r\n" 
//				+"		<td>"+order.getAmount()+"</td>\r\n"
//				+"</tr>"
//				);
		
		for(Book book:order.getOrderedBooks()) {
			invoiceTable.append(
					"</tr>\r\n"
					+"</tr>\r\n"
					+"		<td>"+book.getBookName()+"</td>\r\n"
					+"		<td>"+book.getAuthor().getAuthorName()+"</td>\r\n"
					+"		<td>"+book.getPrice()+"</td>\r\n"
					+"</tr>"
					);
		}
		String invoice="<p style='background-color: lemonchiffon;'>Dear Customer, Thanks for Ordering,Your Order Details Are  indicated Below.<br/><b> Order Id:"+ order.getOrderId()+"<br/>User Name:"+ order.getUser().getName()+"<br/> Order Amount: "+order.getAmount() +"<br/><h3>Order Details :<h3/>"+
				 "</b><p/><table border='1' style='background-color: lemonchiffon;' ><tr>\r\n"
				
				+ "    <th>Order Number</th>\r\n"
				+ "    <th>Customer Name</th>\r\n"
				+ "</tr>\r\n"
				+ "<tr>\r\n"
				
				+ "    <td>"+order.getOrderId()+"</td>\r\n"
				+ "    <td>"+order.getUser().getName()+"</td>\r\n"
				+ "</table><hr/> <h3>Books Details :<h3/> <table border='1' style='background-color: lemonchiffon;'><tr>"
				+ "  <td>Book Name</td>"
				+ "  <td>Author Name </td>"
				+ "  <td>Book Price</td>"
				+invoiceTable+"</table>";
		
			String subject="Order Confirmation: Order Number: "+	order.getOrderId()  +", "+ LocalDate.now();
		
			emailService.sendEmail(subject, order.getUser().getEmail(), invoice);
	}
}
