package com.mth.webquiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {
	
	private int id;
	
	// TODO: Email deve possuir um @ e um . para ser considerado valido
	@NotBlank
	private String email;
	
	@Size(min = 5, max = 100)
	private String password;
	
	// TODO: Integração com Tabela de quiz
	private List<Integer> quizIds = new ArrayList<>();
	
	public  UserDTO() {
		// Vazio
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
}
