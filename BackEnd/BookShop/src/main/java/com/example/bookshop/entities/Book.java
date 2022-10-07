package com.example.bookshop.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bookId;
	
	//@NotNull
	private String bookName;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	
//	@JoinTable(name = "book_author",
//    joinColumns = {@JoinColumn (name = "fk_book") },
//    inverseJoinColumns = {@JoinColumn (name = "fk_author") })    //OPTIONAL
	private List<Author> authors= new ArrayList<>();
	
	//@NotNull
	private double price;
	
}
