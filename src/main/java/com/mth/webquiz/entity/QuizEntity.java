package com.mth.webquiz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mth.webquiz.dto.QuizDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "quiz")
@JsonIgnoreProperties(value = "answer", allowSetters = true)

@Getter @Setter
@NoArgsConstructor
@ToString
public class QuizEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	private String title;
	
	private String text;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizOptions> options = new ArrayList<>();
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizAnswers> answer = new ArrayList<>();
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, 
			fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	public QuizEntity(QuizDTO quizDTO) {
		this.id = quizDTO.getId();
		this.title = quizDTO.getTitle();
		this.text = quizDTO.getText();
		// Adiciona as opções em QuizOptions e vice-versa
		quizDTO.getOptions()
				.forEach(s -> this.options.add(new QuizOptions(s)));
		options.forEach(option -> option.setQuiz(this));
		
		// Adiciona as opções em QuizAnswers e vice-versa
		List<Integer> answersInteger = quizDTO.getAnswer();
		if (answersInteger != null && !answersInteger.isEmpty()) {
			answersInteger.forEach(n -> this.answer.add(new QuizAnswers(n)));
			answer.forEach(answerOpt -> answerOpt.setQuiz(this));
		}
	}
}
