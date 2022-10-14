package com.example.bookshop.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

	private int authorId;

	private String authorName;
	
	private int rating;
	
	public static AuthorDto toDto(Author entity) {
		AuthorDto dto = new AuthorDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static Author toEntity(AuthorDto dto) {
		Author entity = new Author();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public static List<AuthorDto> toDto(List<Author> entityList) {
		return entityList.stream().map(entity -> toDto(entity)).toList();
	}

	public static List<Author> toEntity(List<AuthorDto> dtoList) {
		return dtoList.stream().map(dto -> toEntity(dto)).toList();
	}
}
