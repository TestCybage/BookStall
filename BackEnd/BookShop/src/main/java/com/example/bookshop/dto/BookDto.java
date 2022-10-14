package com.example.bookshop.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Author;
import com.example.bookshop.entities.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

	private int bookId;

	private String bookName;

	private Author author;

	private String description;

	private int inStock;

	private int copiesSold;

	private double price;

	private int rating;

	public static BookDto toDto(Book entity) {
		BookDto dto = new BookDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
	public static List<BookDto> toDto(List<Book> listEntity){
		return listEntity.stream().map(entity -> toDto(entity)).toList();
	}

	public static Book toEntity(BookDto dto) {
		Book entity = new Book();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	
	public static List<Book> toEntity(List<BookDto> listDto){
		return listDto.stream().map(dto -> toEntity(dto)).toList();
	}


}
