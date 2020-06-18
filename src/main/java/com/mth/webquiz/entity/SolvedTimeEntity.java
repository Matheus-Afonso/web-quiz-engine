package com.mth.webquiz.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SolvedTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "primary_id")
	int primaryId;
	
	@Column(name = "reference_id")
	int id;
	
	@Column(name = "completed_at")
	String completedAt;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, 
			fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	UserEntity user;

	public SolvedTimeEntity() {
		// Vazio
	}
	
	public SolvedTimeEntity(int id, String completedAt, UserEntity user) {
		this.id = id;
		this.completedAt = completedAt;
		this.user = user;
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
