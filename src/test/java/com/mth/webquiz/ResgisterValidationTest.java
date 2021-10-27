package com.mth.webquiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mth.webquiz.dto.UserDTO;

@SpringBootTest
class ResgisterValidationTest {
	
	@Autowired
	private Validator validator;
	
	@Test
	void EmailIsValidTest() {
		UserDTO testUserDTO = new UserDTO();
		// E-mail sem @
		testUserDTO.setEmail("invalid.com");
		testUserDTO.setPassword("123456");
		
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(testUserDTO);
		assertFalse(violations.isEmpty());
		
		// E-mail sem o .
		testUserDTO.setEmail("test@error");
		violations = validator.validate(testUserDTO);
		assertFalse(violations.isEmpty());
		
		// E-mail certo
		testUserDTO.setEmail("test@google.com.br");
		violations = validator.validate(testUserDTO);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void PasswordIsValidTest() {
		UserDTO testUserDTO = new UserDTO();
		
		// Menos de 5 caracteres
		testUserDTO.setEmail("test.obj@google.com");
		testUserDTO.setPassword("abcd");
		
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(testUserDTO);
		assertFalse(violations.isEmpty());
		
		// Senha certa
		testUserDTO.setPassword("12345");
		violations = validator.validate(testUserDTO);
		assertTrue(violations.isEmpty());
	}

}
