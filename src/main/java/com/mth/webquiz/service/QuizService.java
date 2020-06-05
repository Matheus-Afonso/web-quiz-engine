package com.mth.webquiz.service;

import java.util.List;
import java.util.Optional;

import com.mth.webquiz.entity.QuizEntity;

public interface QuizService {
	List<QuizEntity> findAll();
	
	Optional<QuizEntity> findById(int id);
	
	void save(QuizEntity theQuiz);
	
	void deleteById(int id);
}
