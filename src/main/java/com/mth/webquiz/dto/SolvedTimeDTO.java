package com.mth.webquiz.dto;

import com.mth.webquiz.entity.SolvedTimeEntity;

import lombok.Data;

@Data
public class SolvedTimeDTO {
	private int id;
	private String completedAt;
	
	public SolvedTimeDTO(SolvedTimeEntity entity) {
		id = entity.getId();
		completedAt = entity.getCompletedAt();
	}	
}
