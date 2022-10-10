package com.example.bookshop.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	@GeneratedValue
	private int bookId;
	
	@NotNull
	private String bookName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Author author;
	
	@NotNull
	private double price;
	
}
