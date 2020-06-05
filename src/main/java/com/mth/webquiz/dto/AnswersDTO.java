package com.mth.webquiz.dto;
// DTO que armazena as opcoes passadas

import java.util.ArrayList;
import java.util.List;

public class AnswersDTO {
	
	private List<Integer> answer = new ArrayList<>();
	
	public AnswersDTO() {
		// Vazio
	}

	public List<Integer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}
}
