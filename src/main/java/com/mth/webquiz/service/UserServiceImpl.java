package com.mth.webquiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
		// Verificação se o email já foi cadastrado
		userRepository.findByEmail(user.getEmail()).
						ifPresent(s -> {
							throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
						});
		
		// Cadastro
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		return userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	


}
