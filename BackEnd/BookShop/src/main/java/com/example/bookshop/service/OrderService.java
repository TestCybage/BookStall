package com.example.bookshop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.ErrorMessage;
import com.example.bookshop.exception.InvalidInputException;
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
	
	@Autowired
	private AddressService addressService;

	Logger logger = Logger.getLogger(OrderService.class);
	
	public List<Orders> getAllOrders(){
		List<Orders> orders = dao.findAllByOrderByOrderIdDesc();
		if(orders==null)
			throw new RecordNotFoundException(ErrorMessage.RECORDS_EMPTY);
		return orders;
	}

	public List<Orders> getOrderByUserName(String userName) {
		Users user = userService.getById(userName);
		if(user==null)
			throw new RecordNotFoundException(ErrorMessage.USER_NOT_FOUND);
		List<Orders> orders = dao.findByUser(user);
		if(orders.isEmpty())
			throw new EmptyRecordException(ErrorMessage.RECORDS_EMPTY); 
		return orders;
	}

	public Orders getById(int id) {
		logger.info(id);
		return dao.findById(id).orElse(null);
	}

	public Orders addOrder(String userName,int addressId) {
		logger.info(userName+""+addressId);
		Address address  = addressService.getById(addressId);
		if(address==null)
			throw new RecordNotFoundException(ErrorMessage.ADDRESS_NOT_FOUND);
		if(!address.getUser().getUserName().equals(userName))
			throw new InvalidInputException(ErrorMessage.INVALID_ADDRESS_ID);
		Cart cart = cartService.getCartByUserName(userName);
		if(cart==null || cart.getBooks().isEmpty())
			throw new EmptyRecordException(ErrorMessage.EMPTY_CART);
		Map<String,Integer> bookMap = new HashMap<>(cart.getBooks());
		logger.info(bookMap);
		Orders order = new Orders();
		order.setBooks(bookMap);
		order.setAmount(cart.getAmount());
		order.setUser(cart.getUser());
		order.setStatus(OrderStatus.COMPLETED);
		order.setAddress(address);
		Set<String> bookNames = cart.getBooks().keySet();
		List<Book> books = new ArrayList<>();
		for(String book:bookNames) {
			books.add(bookService.getBookByName(book));
		}
		dao.save(order);
		for(Book book:books) {
			bookService.editQuantity(book.getBookId(), book.getInStock()-cart.getBooks().get(book.getBookName()));
			bookService.changeCopiesSold(book.getBookId(), cart.getBooks().get(book.getBookName()));
		}
		cartService.emptyCart(cart.getUser().getUserName());
		sendInvoice(order);
		return order;
	}

	public Orders cancelOrder(int id) {
		logger.info(id);
		Orders order = getById(id);
		if (order == null)
			throw new RecordNotFoundException(ErrorMessage.ORDER_NOT_FOUND);
		order.setStatus(OrderStatus.CANCELLED);
		return order;
	}

	public void sendInvoice(Orders order) {
		StringBuilder invoiceTable = new StringBuilder();
		Set<String> bookNames = order.getBooks().keySet();
		List<Book> books = new ArrayList<>() ;
		List<Integer> quantity = new ArrayList<>();
		for(String book:bookNames) {
			books.add(bookService.getBookByName(book));
			quantity.add(order.getBooks().get(book));
		}

		for(int i=0;i<books.size();i++) {
			for(int j=i;j<quantity.size();j++) { 
			invoiceTable.append(
					"</tr>\r\n"
					+"</tr>\r\n"
					+"		<td>"+books.get(i).getBookName()+"</td>\r\n"
					+"		<td>"+books.get(i).getAuthor().getAuthorName()+"</td>\r\n"
					+"		<td>"+books.get(i).getPrice()+"</td>\r\n"
					+"		<td>"+quantity.get(j)+"</td>\r\n"
					+"		<td>"+books.get(i).getPrice()*quantity.get(j)+"</td>\r\n"
					+"</tr>"
					);
				
		}
		}
		String invoice="<p style='background-color: lemonchiffon;'>Dear Customer, Thanks for Ordering,Your Order Details Are  indicated Below.<br/><b> Order Id:"+ order.getOrderId()+"<br/>User Name:"+ order.getUser().getName()+"<br/> Order Amount: "+order.getAmount() +"<br/><h3>Order Details :<h3/>"+
				 "</b><p/><table border='1' style='background-color: lemonchiffon;' ><tr>\r\n"
				
				+ "    <th>Order Number</th>\r\n"
				+ "    <th>Customer Name</th>\r\n"
				+ "</tr>\r\n"
				+ "<tr>\r\n"
				
				+ "    <td>"+order.getOrderId()+"</td>\r\n"
				+ "    <td>"+order.getUser().getName()+"</td>\r\n"
				+ "</table><hr/> <h3>Books Details :<h3/> <table border='1' style='background-color: lemonchiffon;'></tr>"
				+ "  <th>Book Name</th>"
				+ "  <th>Author Name </th>"
				+ "  <th>Book Price</th>"
				+ "  <th>Quantity</th>"
				+ "  <th>Total</th>"
				+invoiceTable+"</table>";
		
			String subject="Order Confirmation: Order Number: "+	order.getOrderId()  +", "+ LocalDate.now();
		
			emailService.sendEmail(subject, order.getUser().getEmail(), invoice);
	}
}
