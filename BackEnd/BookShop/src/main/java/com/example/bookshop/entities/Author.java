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
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int authorId;

	// @NotNull
	private String authorName;

	@ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	
	private List<Book> books = new ArrayList<>();

//	public void addBook(Book b) {
//		this.books.add(b);
//		b.getAuthors().add(this);
//	}
//
//	public void removeBook(Book b) {
//		this.books.remove(b);
//		b.getAuthors().remove(this);
//	}

}
