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
import javax.persistence.Table;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "answer_opt")
	private int answerOpt;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id")
	private QuizEntity quiz;
	
	public QuizAnswers() {
		// Vazio
	}

	public QuizAnswers(int answerOpt) {
		this.answerOpt = answerOpt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnswerOpt() {
		return answerOpt;
	}

	public void setAnswerOpt(int answerOpt) {
		this.answerOpt = answerOpt;
	}
	
	public QuizEntity getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizEntity quiz) {
		this.quiz = quiz;
	}

	@Override
	public String toString() {
		return "QuizAnswers [id=" + id + ", answerOpt=" + answerOpt + "]";
	}
}
