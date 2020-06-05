package com.mth.webquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.UserRepository;
import com.mth.webquiz.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserEntity registerUser(UserEntity user) {
		// TODO: Verificação se o email já foi cadastrado
		
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		return userRepository.save(user);
	}

}
