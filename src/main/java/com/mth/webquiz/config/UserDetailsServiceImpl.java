package com.mth.webquiz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.UserRepository;
import com.mth.webquiz.entity.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		log.info(">>>Email passado: {}", email);
		log.info(">>>User achado: {}", user);
		if(user == null) {
			throw new UsernameNotFoundException("Email nao encontrado");
		}
		
		return toUserDetails(user);
	}
	
	private UserDetails toUserDetails(UserEntity user) {
		return User.withUsername(user.getEmail())
					.password(user.getPassword())
					.roles("USER")
					.build();
	}

}
