package com.mth.webquiz.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import com.mth.webquiz.dto.UserDTO;

@Entity(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String email;
	
	private String password;
	
	// Integração com tabela de quiz. Para ser usada no DELETE
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<QuizEntity> quizzes = new ArrayList<>();
	
	// Integração com a tabela dos timestamps das resoluções
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SolvedTimeEntity> solvedTimes = new ArrayList<>();
	
	public UserEntity() {
		// Vazio
	}
	
	public UserEntity(UserDTO userDTO) {
		// Só irá acessar quando um novo user for criado
		id = userDTO.getId();
		email = userDTO.getEmail();
		password = userDTO.getPassword();
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
	
	public List<QuizEntity> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<QuizEntity> quizzes) {
		this.quizzes = quizzes;
	}
	
	public List<SolvedTimeEntity> getSolvedTimes() {
		return solvedTimes;
	}

	public void setSolvedTimes(List<SolvedTimeEntity> solvedTimes) {
		this.solvedTimes = solvedTimes;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", email=" + email + ", password=" + password + "]";
	}

}
