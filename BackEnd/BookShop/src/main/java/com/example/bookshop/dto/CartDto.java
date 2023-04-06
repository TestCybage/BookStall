package com.example.bookshop.dto;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Cart;
import com.example.bookshop.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

	private int cartId;

	private Map<String, Integer> books;
	
	private double amount;

	private Users user;
	
	public static CartDto toDto(Cart entity) {
		CartDto dto = new CartDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
	public static Cart toEntity(CartDto dto) {
		Cart entity = new Cart();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
}
