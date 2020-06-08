package com.mth.webquiz.service;

import java.util.Optional;

import com.mth.webquiz.entity.UserEntity;

public interface UserService {
	UserEntity registerUser(UserEntity user);
	
	Optional<UserEntity> findByEmail(String email);
}
