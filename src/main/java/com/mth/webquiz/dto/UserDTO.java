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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(value = "password", allowSetters = true)

@Data
@NoArgsConstructor
public class UserDTO implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ToString.Exclude
	private int id;
	
	@EmailConstraint
	private String email;
	
	@Size(min = 5, max = 100)
	private String password;
	
	public UserDTO(UserEntity userEntity) {
		id = userEntity.getId();
		email = userEntity.getEmail();
		password = userEntity.getPassword();
	}
	
	@Override
	public String toString() {
		return "UserEntity [email=" + email + ", password=" + password + "]";
	}
	
	// Tudo abaixo é para Spring Security
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
