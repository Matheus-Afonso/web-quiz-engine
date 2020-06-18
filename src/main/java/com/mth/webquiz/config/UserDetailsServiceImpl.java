package com.mth.webquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.UserRepository;
import com.mth.webquiz.dto.UserDTO;
import com.mth.webquiz.entity.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		UserEntity entity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email nao encontrado"));
		
		return new UserDTO(entity);
		
	}
	
}
