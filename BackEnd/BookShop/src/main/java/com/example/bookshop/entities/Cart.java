package com.example.bookshop.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue
	private int cartId;

	@ElementCollection
	@MapKeyColumn(name = "book_name")
    @Column(name = "quantity")
	private Map<String, Integer> books = new HashMap<>();

	private double amount;

	@OneToOne
	private Users user;

}
