package com.mth.webquiz.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mth.webquiz.dto.UserDTO;
import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	@PostMapping("/register")
	public AnswerFeedback registerUser(@Valid @RequestBody UserDTO user, Errors errors) {
		
		if (errors.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalidFieldErrorMessage(errors));
		}
		
		UserEntity entity = new UserEntity(user);
		userService.registerUser(entity);
		return new AnswerFeedback(true, "Usuário de email " + user.getEmail() + " criado");
	}
	
	private String invalidFieldErrorMessage(Errors errors) {
		StringBuilder msg = new StringBuilder();
		msg.append(errors.getFieldError().getField())
			.append(": ")
			.append(errors.getFieldError().getDefaultMessage());
		
		return msg.toString();
	}
}
