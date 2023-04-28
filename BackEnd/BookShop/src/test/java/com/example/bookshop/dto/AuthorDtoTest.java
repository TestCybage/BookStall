package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Author;
@SpringBootTest
class AuthorDtoTest {
	
	private Author author;
	
	private AuthorDto authorDto;
	
	private AuthorDto dto1;
	
	private AuthorDto dto2;
	
	public AuthorDtoTest() {
		author = new Author();
        author.setAuthorId(1);
        author.setAuthorName("Test Author");
        author.setRating(4);

        authorDto = new AuthorDto();
        authorDto.setAuthorId(1);
        authorDto.setAuthorName("Test Author");
        authorDto.setRating(4);
        
        dto1 = new AuthorDto();
        dto2 = new AuthorDto();
	}
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCode() {
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
	
	@Test
    @DisplayName("Test hashCode method")
    void testHashCodeNotEquals() {
        assertNotEquals(dto1.hashCode(), authorDto.hashCode());
    }
	
	@Test
    @DisplayName("Test equals method with equal objects")
    void testEqualsWithEqualObjects() {
        assertTrue(dto1.equals(dto2));
    }

    @Test
    @DisplayName("Test equals method with non-equal objects")
    void testEqualsWithNonEqualObjects() {
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(authorDto));
        assertFalse(dto1.equals(new Object()));
    }
    
    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "AuthorDto";
        String actual = dto1.toString().substring(0, 9);
        assertEquals(expected, actual);
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
