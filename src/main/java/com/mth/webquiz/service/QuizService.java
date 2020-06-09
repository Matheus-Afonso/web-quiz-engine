package com.mth.webquiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

public interface QuizService {

	Page<QuizEntity> findAll(int page, int pageSize, String sortBy);
	
	Optional<QuizEntity> findById(int id);
	
	void save(QuizEntity theQuiz);
	
	void deleteById(int id);
	
	List<QuizEntity> findByUser(UserEntity user);

	boolean deleteByIdAndUser(int quizId, UserEntity userEntity);

}
