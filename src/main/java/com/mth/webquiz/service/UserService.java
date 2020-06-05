package com.mth.webquiz.service;

import com.mth.webquiz.entity.UserEntity;

public interface UserService {
	UserEntity registerUser(UserEntity user);
	
	UserEntity findByEmail(String email);
}
