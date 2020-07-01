package com.mth.webquiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mth.webquiz.entity.QuizEntity;

// Classe responsável por receber o JSON
@JsonIgnoreProperties(value = "answer", allowSetters = true)
public class QuizDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private int id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String text;
	
	@Size(min = 2, max = 20)
	// Não pode ter menos de 2 opções
	private List<String> options = new ArrayList<>();
	
	private List<Integer> answer = new ArrayList<>();
	
	public QuizDTO() {
		// Vazio
	}
	
	public QuizDTO(QuizEntity entity) {
		// Converte de Entity para um DTO
		id = entity.getId();
		title = entity.getTitle();
		text = entity.getText();
		entity.getOptions()
				.forEach(opt -> options.add(opt.getOption()));
		entity.getAnswer()
				.forEach(ans -> answer.add(ans.getAnswerOpt()));
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

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public List<Integer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", text=" + text + ", options=" + options + ", answers="
				+ answer + "]";
	}
}
