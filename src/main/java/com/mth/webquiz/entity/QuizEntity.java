package com.mth.webquiz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mth.webquiz.dto.QuizDTO;

@Entity(name = "quiz")
@JsonIgnoreProperties(value = "answer", allowSetters = true)
public class QuizEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String text;
	
	@Size(min = 2, max = 20)
	// Se deletar a pergunta, tudo relacionado a ela é deletado
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<QuizOptions> options = new ArrayList<>();
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<QuizAnswers> answer = new ArrayList<>();
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	public QuizEntity() {
		// Vazio para DB
	}
	
	public QuizEntity(QuizDTO quizDTO) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<QuizOptions> getOptions() {
		return options;
	}

	public void setOptions(List<QuizOptions> options) {
		this.options = options;
	}
	
	public List<QuizAnswers> getAnswer() {
		return answer;
	}

	public void setAnswer(List<QuizAnswers> answer) {
		this.answer = answer;
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", text=" + text + ", options=" + options + "]";
	}

}
