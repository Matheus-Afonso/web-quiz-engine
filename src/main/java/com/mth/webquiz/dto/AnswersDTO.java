package com.mth.webquiz.dto;
// DTO que armazena as opcoes passadas

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AnswersDTO {
	private List<Integer> answer = new ArrayList<>();
}
