package com.example.bookshop.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

	private int id;

	private String addressLine;

	private String city;

	private int pin;

	private Users user;

	public static Address toEntity(AddressDto dto) {
		Address entity = new Address();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public static AddressDto toDto(Address entity) {
		AddressDto dto = new AddressDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static List<Address> toEntity(List<AddressDto> listDto) {
		return listDto.stream().map(AddressDto::toEntity).toList();
	}

	public static List<AddressDto> toDto(List<Address> listEntity) {
		return listEntity.stream().map(AddressDto::toDto).toList();
	}
}
