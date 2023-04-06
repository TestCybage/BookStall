package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;

@SpringBootTest
class BookDtoTest {
	
	private Author author = new Author(100, "tester", null, 3);
	private Book book = new Book();
	private BookDto bookDto = new BookDto();

	public BookDtoTest() {
		book.setBookId(100);
		book.setBookName("testBook");
		book.setAuthor(author);
		book.setPrice(100);
		
		bookDto.setBookId(100);
		bookDto.setBookName("testBook");
		bookDto.setAuthor(author);
		bookDto.setPrice(100);
	}
	
	@Test
	void testToDtoBook() {
		BookDto dto = BookDto.toDto(book);
		assertEquals(bookDto, dto);
	}

	@Test
	void testToDtoListOfBook() {
		List<Book> entityList = new ArrayList<>();
		entityList.add(book);
		List<BookDto> dtoList = BookDto.toDto(entityList);
		assertEquals(1, dtoList.size());
		assertEquals(bookDto, dtoList.get(0));
 	}

	@Test
	void testToEntityBookDto() {
		Book entity = BookDto.toEntity(bookDto);
		assertEquals(book, entity);
	}

	@Test
	void testToEntityListOfBookDto() {
		List<BookDto> dtoList = new ArrayList<>();
		dtoList.add(bookDto);
		List<Book> entityList = BookDto.toEntity(dtoList);
		assertEquals(1, entityList.size());
		assertEquals(book, entityList.get(0));
	}

}
