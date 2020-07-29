package com.mth.webquiz.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

public interface QuizService {

	Page<QuizEntity> findAll(int page, int pageSize, String sortBy);
	
	Optional<QuizEntity> findById(int id);
	
	void save(QuizEntity theQuiz);
	
	boolean deleteByIdAndUser(int quizId, UserEntity userEntity);

	Page<QuizEntity> findAllByUser(int page, int pageSize, String sortBy, UserEntity userEntity);

}
