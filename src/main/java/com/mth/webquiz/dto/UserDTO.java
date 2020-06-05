package com.mth.webquiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.mth.webquiz.entity.UserEntity;

public class UserDTO {
		
	// TODO: Email deve possuir um @ e um . para ser considerado valido
	@NotBlank
	private String email;
	
	@Size(min = 5, max = 100)
	private String password;
	
	private List<Integer> quizIds = new ArrayList<>();
	
	public UserDTO() {
		// Vazio
	}
	
	public  UserDTO(UserEntity userEntity) {
		email = userEntity.getEmail();
		password = userEntity.getPassword();
		userEntity.getQuizzes()
					.forEach(quiz -> quizIds.add(quiz.getId()));
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
	
	public List<Integer> getQuizIds() {
		return quizIds;
	}

	public void setQuizIds(List<Integer> quizIds) {
		this.quizIds = quizIds;
	}

	@Override
	public String toString() {
		return "UserEntity [email=" + email + ", password=" + password + "]";
	}
}
