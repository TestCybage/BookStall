package com.example.bookshop.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Orders;
import com.example.bookshop.entities.UserRole;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int userId;

	private String name;

	private String email;

	private String password;

	private String mobileNo;

	private UserStatus status;

	private List<Orders> orders;

	private UserRole role;

	public static UserDto toDto(Users entity) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static Users toEntity(UserDto dto) {
		Users entity = new Users();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public static List<UserDto> toDto(List<Users> entityList) {
		return entityList.stream().map(entity -> toDto(entity)).toList();
	}

	public static List<Users> toEntity(List<UserDto> dtoList) {
		return dtoList.stream().map(dto -> toEntity(dto)).toList();
	}

}
