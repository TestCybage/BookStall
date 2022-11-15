package com.example.bookshop.dto;

import org.springframework.beans.BeanUtils;

import com.example.bookshop.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
	
	private String roleName;

	private String roleDescription;
	
	public static RoleDto toDto(Role entity) {
		RoleDto dto = new RoleDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static Role toEntity(RoleDto dto) {
		Role entity = new Role();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

}
