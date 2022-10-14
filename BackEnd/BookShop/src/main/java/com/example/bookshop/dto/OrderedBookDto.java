package com.example.bookshop.dto;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Book;
import com.example.bookshop.entities.OrderedBook;
import com.example.bookshop.entities.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedBookDto {

	private int orderedBookId;

	private Book book;

	private Orders order;

	@Min(0)
	private int quantity;

	private double amount;

	public static OrderedBookDto toDto(OrderedBook entity) {
		OrderedBookDto dto = new OrderedBookDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static OrderedBook toEntity(OrderedBookDto dto) {
		OrderedBook entity = new OrderedBook();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public static List<OrderedBookDto> toDto(List<OrderedBook> entityList) {
		return entityList.stream().map(entity -> toDto(entity)).toList();
	}

	public static List<OrderedBook> toEntity(List<OrderedBookDto> dtoList) {
		return dtoList.stream().map(dto -> toEntity(dto)).toList();
	}
}
