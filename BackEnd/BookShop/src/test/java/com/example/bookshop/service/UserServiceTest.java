package com.example.bookshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.bookshop.dto.Credentials;
import com.example.bookshop.entities.Role;
import com.example.bookshop.entities.UserStatus;
import com.example.bookshop.entities.Users;
import com.example.bookshop.exception.AlreadyEnabledException;
import com.example.bookshop.exception.AlreadyExistException;
import com.example.bookshop.exception.EmptyRecordException;
import com.example.bookshop.exception.InvalidInputException;
import com.example.bookshop.exception.RecordNotFoundException;
import com.example.bookshop.exception.ShortPasswordException;
import com.example.bookshop.repository.RoleRepo;
import com.example.bookshop.repository.UserRepo;

@SpringBootTest
class UserServiceTest {

	Users user = new Users();
	String id = "aashay";
	String name = "Aashay Kadu";
	String email = "aashay@test.com";
	String pass = "aashay@test";
	String mobile = "1234567890";

	Role userRole = new Role();
	Role adminRole = new Role();
	Set<Role> roles = new HashSet<>();

	@BeforeEach
	void setup() {

		user.setUserName(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(pass);
		user.setMobileNo(mobile);
		user.setStatus(UserStatus.ENABLED);

		userRole.setRoleName("USER");
		userRole.setRoleDescription("Default role for the user");

		adminRole.setRoleName("ADMIN");
		adminRole.setRoleDescription("ADMIN role");

		roles.add(userRole);
		user.setRole(roles);
	}

	@Autowired
	private UserService service;

	@MockBean
	private UserRepo dao;

	@MockBean
	private RoleRepo roleDao;

	@MockBean
	private PasswordEncoder encoder;

	@Test
	void testGetUserByEmail() {
		when(dao.findByEmail(email)).thenReturn(user);
		assertThat(service.getUserByEmail(email)).isEqualTo(user);
	}

	@Test
	void testSignUp() {

		when(dao.findById(id)).thenReturn(Optional.ofNullable(null));
		when(dao.findByEmail(email)).thenReturn(null);

		Optional<Role> role = Optional.ofNullable(userRole);
		when(roleDao.findById("USER")).thenReturn(role);
		when(encoder.encode(pass)).thenReturn(pass);
		when(dao.save(user)).thenReturn(user);
		assertThat(service.signUp(user)).isEqualTo(user);
	}
	
	@Test
	void testSignUpUserNameExists() {
		when(dao.findById(id)).thenReturn(Optional.ofNullable(user));
		assertThrows(AlreadyExistException.class, ()->service.signUp(user));
	}
	
	@Test
	void testSignUpEmailExists() {
		when(dao.findById(id)).thenReturn(Optional.ofNullable(null));
		when(dao.findByEmail(email)).thenReturn(user);
		assertThrows(AlreadyExistException.class, ()->service.signUp(user));
	}
	
	@Test
	void testSignUpShortPassword() {
		String password = "123";
		user.setPassword(password);
		assertEquals(password, user.getPassword());
		when(dao.findById(id)).thenReturn(Optional.ofNullable(null));
		when(dao.findByEmail(email)).thenReturn(null);
		assertThrows(ShortPasswordException.class, ()->service.signUp(user));
	}

	@Test
	void testGetEncodedPassword() {
		when(encoder.encode(pass)).thenReturn(pass);
		assertThat(service.getEncodedPassword(pass)).isEqualTo(pass);
	}

	@Test
	void testUnblockUser() {
		user.setStatus(UserStatus.DISABLED);
		when(dao.findByEmail(email)).thenReturn(user);
		when(dao.save(user)).thenReturn(user);
		assertThat(service.unblockUser(email)).isEqualTo(user);
	}
	
	@Test
	void testUnblockUserNotFound() {
		user.setStatus(UserStatus.DISABLED);
		when(dao.findByEmail("abc@g.com")).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.unblockUser("abc@g.com"));
	}
	
	@Test
	void testUnblockUserNotDisabled() {
		when(dao.findByEmail(email)).thenReturn(user);
		assertThrows(AlreadyEnabledException.class,()->service.unblockUser(email));
	}

	@Test
	void testGetById() {
		Optional<Users> user1 = Optional.ofNullable(user);
		when(dao.findById(id)).thenReturn(user1);
		assertThat(service.getById(id)).isEqualTo(user);
	}

	@Test
	void testDisableUser() {
		when(dao.findById(id)).thenReturn(Optional.ofNullable(user));
		user.setStatus(UserStatus.DISABLED);
		when(dao.save(user)).thenReturn(user);
		assertThat(service.disableUser(id)).isEqualTo(user);
	}
	
	@Test
	void testDisableUserNotFound() {
		when(dao.findById(id)).thenReturn(Optional.empty());
		assertThrows(RecordNotFoundException.class, ()->service.disableUser(id));
	}

	@Test
	void testGetDisabledUsers() {
		user.setStatus(UserStatus.DISABLED);
		List<Users> users = new ArrayList<>();
		users.add(user);
		when(dao.findByStatus(UserStatus.DISABLED)).thenReturn(users);
		assertThat(service.getDisabledUsers()).isEqualTo(users);
	}
	
	@Test
	void testGetDisabledUsersEmptyList() {
		List<Users> users = new ArrayList<>();
		when(dao.findByStatus(UserStatus.DISABLED)).thenReturn(users);
		assertThrows(EmptyRecordException.class, ()->service.getDisabledUsers());
	}
	
	@Test
	void testGetAllUsers() {
		List<Users> users = new ArrayList<>();
		users.add(user);
		when(dao.findAll()).thenReturn(users);
		assertNotNull(users);
		assertEquals(1, users.size());
	}
	
	@Test
	void testGetAllUsersEmptyList() {
		List<Users> users = new ArrayList<>();
		when(dao.findAll()).thenReturn(users);
		assertThrows(EmptyRecordException.class, ()->service.getAllUsers());
	}
	
	@Test
	void testForgotPassword() {
		String newPassword = "aashay@123";
		Credentials cred = new Credentials(email, newPassword); 
		when(dao.findByEmail(cred.getUserEmail())).thenReturn(user);
		when(encoder.encode(newPassword)).thenReturn(newPassword);
		assertNotEquals(newPassword, pass);
		when(dao.save(user)).thenReturn(user);
		Users user1 = service.forgetPassword(cred);
		assertEquals(user, user1);
	}
	
	@Test
	void testForgotPasswordUserNotFound() {
		String newPassword = "aashay@123";
		Credentials cred = new Credentials(email, newPassword); 
		when(dao.findByEmail(cred.getUserEmail())).thenReturn(null);
		assertThrows(RecordNotFoundException.class, ()->service.forgetPassword(cred));
	}
	
	@Test
	void testForgotPasswordInvalidInput() {
		String newPassword = "aashay@test";
		Credentials cred = new Credentials(email, newPassword); 
		when(dao.findByEmail(cred.getUserEmail())).thenReturn(user);
		when(encoder.encode(newPassword)).thenReturn(newPassword);
		assertEquals(pass, newPassword);
		assertThrows(InvalidInputException.class, ()->service.forgetPassword(cred));
	}

	@Test
	void testInitRolesAndUsers() {

		Users adminUser = new Users();
		adminUser.setUserName("Admin");
		adminUser.setName("Admin");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setMobileNo("1234567980");
		adminUser.setPassword(encoder.encode("admin@pass"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);

		when(roleDao.save(adminRole)).thenReturn(adminRole);
		when(roleDao.save(userRole)).thenReturn(userRole);
		when(dao.save(adminUser)).thenReturn(adminUser);

		service.initRolesAndUsers();

		verify(roleDao, times(1)).save(adminRole);
		verify(roleDao, times(1)).save(userRole);
		verify(dao, times(1)).save(adminUser);
	}

}
