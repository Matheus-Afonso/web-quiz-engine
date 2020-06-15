package com.mth.webquiz.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "quiz_options")
@JsonIgnoreProperties(value = {"quiz", "id"})
public class QuizOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String option;
	
	// Vários option para um quiz: Many-to-One. Não pode cascatear delete
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id")
	private QuizEntity quiz;
	
	public QuizOptions() {
		// Vazio
	}
	
	public QuizOptions(String option) {
		this.option = option;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public QuizEntity getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizEntity quiz) {
		this.quiz = quiz;
	}

	@Override
	public String toString() {
		return "QuizOptions [id=" + id + ", option=" + option;
	}
}
