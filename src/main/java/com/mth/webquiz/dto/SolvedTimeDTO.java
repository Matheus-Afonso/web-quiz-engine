package com.mth.webquiz.dto;

import com.mth.webquiz.entity.SolvedTimeEntity;

public class SolvedTimeDTO {
	private int id;
	private String completedAt;
	
	public SolvedTimeDTO() {
		// Vazio
	}
	
	public SolvedTimeDTO(SolvedTimeEntity entity) {
		id = entity.getId();
		completedAt = entity.getCompletedAt();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
	
	
}
