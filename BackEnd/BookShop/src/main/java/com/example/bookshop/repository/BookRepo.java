package com.example.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.entities.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	public Book findByBookName(String bookName);

}
