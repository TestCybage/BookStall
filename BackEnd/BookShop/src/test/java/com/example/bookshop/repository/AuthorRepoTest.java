package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;

@SpringBootTest
class AuthorRepoTest {
	
	@Autowired
	private AuthorRepo dao;
	
	String name = "J K Rowling";
	
	@AfterEach
	void clear() {
		dao.delete(dao.findByAuthorName(name));
	}
	
	@Test
	void testFindByAuthorName() {
		Author author = new Author();
		author.setAuthorName(name);
		dao.save(author);
		
		Author testAuthor = dao.findByAuthorName(name);
		assertNotNull(testAuthor);
		assertEquals(name, testAuthor.getAuthorName());
	}

}
