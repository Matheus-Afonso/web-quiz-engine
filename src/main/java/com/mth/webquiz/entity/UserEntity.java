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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String email;
	
	@ToString.Exclude
	private String password;
	
	// Integração com tabela de quiz. Para ser usada no DELETE
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<QuizEntity> quizzes = new ArrayList<>();
	
	// Integração com a tabela dos timestamps das resoluções
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<SolvedTimeEntity> solvedTimes = new ArrayList<>();
	
	public UserEntity(UserDTO userDTO) {
		// Só irá acessar quando um novo user for criado
		id = userDTO.getId();
		email = userDTO.getEmail();
		password = userDTO.getPassword();
	}
}
