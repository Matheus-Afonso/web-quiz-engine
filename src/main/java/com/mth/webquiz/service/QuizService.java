package com.mth.webquiz.service;

import java.util.List;
import java.util.Optional;

import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

public interface QuizService {
	List<QuizEntity> findAll();
	
	Optional<QuizEntity> findById(int id);
	
	void save(QuizEntity theQuiz);
	
	void deleteById(int id);
	
	List<QuizEntity> findByUser(UserEntity user);

	Optional<QuizEntity> findByIdAndUser(int id, UserEntity user);

	boolean deleteByIdAndUser(int quizId, UserEntity userEntity);
}
