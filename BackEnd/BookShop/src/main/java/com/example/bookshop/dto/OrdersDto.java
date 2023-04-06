package com.example.bookshop.dto;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.OrderStatus;
import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

	private int orderId;

	private Map<String, Integer> books;
	
	private Address address;

	private OrderStatus status;
	
	private double amount;

	private Users user;

	public static OrdersDto toDto(Orders entity) {
		OrdersDto dto = new OrdersDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static Orders toEntity(OrdersDto dto) {
		Orders entity = new Orders();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public static List<OrdersDto> toDto(List<Orders> entityList) {
		return entityList.stream().map(OrdersDto::toDto).toList();
	}

	public static List<Orders> toEntity(List<OrdersDto> dtoList) {
		return dtoList.stream().map(OrdersDto::toEntity).toList();
	}
}
