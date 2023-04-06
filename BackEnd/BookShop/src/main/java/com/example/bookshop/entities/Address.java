package com.example.bookshop.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue
	private int id;
	
	private String addressLine;
	
	@NotNull
	private String city;
	
	@Min(100000)
	@Max(999999)
	@NotNull
	private int pin;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Users user;
	
}
