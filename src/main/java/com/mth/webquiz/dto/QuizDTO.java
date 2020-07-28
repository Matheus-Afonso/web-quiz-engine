package com.mth.webquiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mth.webquiz.entity.QuizEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

// Classe responsável por receber o JSON
@JsonIgnoreProperties(value = "answer", allowSetters = true)

@Data
@NoArgsConstructor
public class QuizDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private int id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String text;
	
	@Size(min = 2, max = 20)
	private List<String> options = new ArrayList<>();
	
	private List<Integer> answer = new ArrayList<>();
	
	public QuizDTO(QuizEntity entity) {
		id = entity.getId();
		title = entity.getTitle();
		text = entity.getText();
		entity.getOptions()
				.forEach(opt -> options.add(opt.getOption()));
		entity.getAnswer()
				.forEach(ans -> answer.add(ans.getAnswerOpt()));
	}
}
