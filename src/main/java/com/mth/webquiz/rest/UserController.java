package com.mth.webquiz.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	@PostMapping("/register")
	public UserEntity registerUser(@Valid @RequestBody UserEntity user) {
		user.setId(0);
		return userService.registerUser(user);
	}
}
