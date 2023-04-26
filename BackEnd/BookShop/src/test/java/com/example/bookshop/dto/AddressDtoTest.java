package com.example.bookshop.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bookshop.entities.Address;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;

@SpringBootTest
class AddressDtoTest {

	private Address address;
	
	private AddressDto addressDto;
	
	private Role role  = new Role("tester", "test");
	
	private Set<Role> roles = new HashSet<>();
	
	private Users user = new Users();
	
	private Users user1;
	
	@BeforeEach
	void setup() {
		roles.add(role);
		user.setUserName("Tester");
		user.setName("test");
		user.setEmail("test@test.com");
		user.setPassword("test@pass");
		user.setMobileNo("8899775588");
		user.setStatus(UserStatus.ENABLED);
		user.setRole(roles);
		
		user1 = new Users("test", "test", "test@ku.com", "13154", "2211554433", UserStatus.ENABLED, null, null, roles);
		
		address = new Address(100, "Pimpri", "Pune", 414824,user);
		
		addressDto = new AddressDto(100, "Pimpri", "Pune", 414824, user);
	}
	
	@Test
	void testAddressDto() {
		AddressDto dto=new AddressDto(100, "Pimpri", "Pune", 414824, user);
		assertEquals(100, dto.getId());
		assertEquals("Pimpri",dto.getAddressLine());
		assertEquals("Pune",dto.getCity());
		assertEquals(414824,dto.getPin());
		assertEquals(user, dto.getUser());
		dto.setId(101);
		dto.setAddressLine("Rajapeth");
		dto.setCity("Amravati");
		dto.setPin(444606);
		dto.setUser(user1);
		assertEquals(101, dto.getId());
		assertEquals("Rajapeth",dto.getAddressLine());
		assertEquals("Amravati",dto.getCity());
		assertEquals(444606,dto.getPin());
		assertEquals(user1, dto.getUser());
	}
	
	@Test
	void testToEntityAddressDto() {
		Address entity = AddressDto.toEntity(addressDto);
		assertEquals(address, entity);
	}

	@Test
	void testToDtoAddress() {
		AddressDto dto = AddressDto.toDto(address);
		assertEquals(addressDto, dto);
	}

	@Test
	void testToEntityListOfAddressDto() {
		List<AddressDto> dtoList = new ArrayList<>();
		dtoList.add(addressDto);
		List<Address> entityList = AddressDto.toEntity(dtoList);
		assertEquals(1, entityList.size());
		assertEquals(address, entityList.get(0));
	}

	@Test
	void testToDtoListOfAddress() {
		List<Address> entityList = new ArrayList<>();
		entityList.add(address);
		List<AddressDto> dtoList = AddressDto.toDto(entityList);
		assertEquals(1, dtoList.size());
		assertEquals(addressDto, dtoList.get(0));
	}

}
