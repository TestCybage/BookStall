package com.example.bookshop;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;
import com.example.bookshop.repository.AuthorRepo;

@SpringBootTest
class BookShopApplicationTests {

	@Autowired
	private AuthorRepo authorDao;
	
	Logger logger = Logger.getLogger(BookShopApplicationTests.class);
	
	@Test
	void saveAuthor() {
		
		Book book = new Book();
		book.setBookId(100);
		book.setBookName("Shamchi Aai");
		book.setPrice(150.0);
		
		Book book1 = new Book();
		book1.setBookId(101);
		book1.setBookName("Certificate of geography");
		book1.setPrice(200.0);
		
		Author author = new Author();
		author.setAuthorId(100);
		author.setAuthorName("Saane Guruji");
		
		author.getBooks().add(book);
		author.getBooks().add(book1);
		
		book.getAuthors().add(author);
		book1.getAuthors().add(author);
		
		authorDao.save(author);
	}
	
	@Test
	void getAuthor() {
		List<Author> authors = authorDao.findAll();
		authors.forEach((author) -> {
			logger.debug(author.getAuthorName());
			author.getBooks().forEach((book) ->{
				logger.debug(book.getBookName());
			});
		});
	}
	

}
