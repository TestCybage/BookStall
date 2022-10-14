package com.example.bookshop.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedBook {
	
	@Id
	@GeneratedValue
	private int orderedBookId;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Book book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Orders order;

	@Min(0)
	private int quantity;

	private double amount;
}
