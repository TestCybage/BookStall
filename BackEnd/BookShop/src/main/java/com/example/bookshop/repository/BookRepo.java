package com.example.bookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	public Book findByBookName(String bookName);
	
	public List<Book> findByAuthor(Author author);
	
	public List<Book> findByBookNameContaining(String bookName);

}
