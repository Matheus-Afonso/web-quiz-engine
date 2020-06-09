package com.mth.webquiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = "primaryId")
public class SolvedTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "primary_id")
	int primaryId;
	
	@Column(name = "reference_id")
	int id;
	
	@Column(name = "completed_at")
	String completedAt;

	public SolvedTimeEntity() {
		// Vazio
	}
	
	public SolvedTimeEntity(int id, String completedAt) {
		this.id = id;
		this.completedAt = completedAt;
	}

	public int getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
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
