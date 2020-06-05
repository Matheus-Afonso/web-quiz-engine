package com.mth.webquiz.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mth.webquiz.dto.UserDTO;
import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	@PostMapping("/register")
	public UserEntity registerUser(@Valid @RequestBody UserDTO user) {
		UserEntity entity = new UserEntity(user);
		return userService.registerUser(entity);
	}
}
