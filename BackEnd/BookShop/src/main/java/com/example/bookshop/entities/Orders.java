package com.example.bookshop.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@GeneratedValue
	private int orderId;
	
	@ElementCollection
	@MapKeyColumn(name = "book_name")
    @Column(name = "quantity")
	private Map<String, Integer> books = new HashMap<>();
	
	@NotNull
	@OneToOne
	private Address address;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@NotNull
	private double amount;

	@ManyToOne(fetch = FetchType.EAGER)
	private Users user;

}
