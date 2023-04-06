package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;
@SpringBootTest
class AuthorDtoTest {
	
	private Author author;
	
	private AuthorDto authorDto;
	
	public AuthorDtoTest() {
		author = new Author();
        author.setAuthorId(1);
        author.setAuthorName("Test Author");
        author.setRating(4);

        authorDto = new AuthorDto();
        authorDto.setAuthorId(1);
        authorDto.setAuthorName("Test Author");
        authorDto.setRating(4);
	}
	

	@Test
	void testToDtoAuthor() {
		 AuthorDto dto = AuthorDto.toDto(author);
	     assertEquals(authorDto, dto);
	}

	@Test
	void testToEntityAuthorDto() {
		 Author entity = AuthorDto.toEntity(authorDto);
	     assertEquals(author, entity);
	}

	@Test
	void testToDtoListOfAuthor() {
		List<Author> entityList = new ArrayList<>();
        entityList.add(author);
        List<AuthorDto> dtoList = AuthorDto.toDto(entityList);
        assertEquals(1, dtoList.size());
        assertEquals(authorDto, dtoList.get(0));
	}

	@Test
	void testToEntityListOfAuthorDto() {
		List<AuthorDto> dtoList = new ArrayList<>();
        dtoList.add(authorDto);
        List<Author> entityList = AuthorDto.toEntity(dtoList);
        assertEquals(1, entityList.size());
        assertEquals(author, entityList.get(0));
	}

}
