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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "quiz_options")
@JsonIgnoreProperties(value = {"quiz", "id"})

@Getter	@Setter
@NoArgsConstructor
@ToString
public class QuizOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String option;
	
	// Vários option para um quiz: Many-to-One. Não pode cascatear delete
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id")
	@ToString.Exclude 
	private QuizEntity quiz;
	
	public QuizOptions(String option) {
		this.option = option;
	}
}
