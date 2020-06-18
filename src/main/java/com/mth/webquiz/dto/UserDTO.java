package com.mth.webquiz.dto;

import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.validator.EmailConstraint;

@JsonIgnoreProperties(value = "password", allowSetters = true)
public class UserDTO implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private int id;
	
	@EmailConstraint
	private String email;
	
	@Size(min = 5, max = 100)
	private String password;
	
	public UserDTO() {
		// Vazio
	}
	
	public UserDTO(UserEntity userEntity) {
		id = userEntity.getId();
		email = userEntity.getEmail();
		password = userEntity.getPassword();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserEntity [email=" + email + ", password=" + password + "]";
	}
	
	// Para Spring Security
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
