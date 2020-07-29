package com.mth.webquiz.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.QuizRepository;
import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	QuizRepository quizRepository;
	
	@Override
	public void save(QuizEntity theQuiz) {
		quizRepository.save(theQuiz);
	}

	@Override
	public Page<QuizEntity> findAll(int page, int pageSize, String sortBy) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
		return quizRepository.findAll(pageable);
	}

	@Override
	public Optional<QuizEntity> findById(int id) {
		return quizRepository.findById(id);
	}

	@Override
	public boolean deleteByIdAndUser(int quizId, UserEntity userEntity) {
		QuizEntity quiz = quizRepository.findById(quizId).orElseThrow(NoSuchElementException::new);
		if(quiz.getUser().getId() == userEntity.getId()) {
			quizRepository.deleteById(quizId);
			return true;
		}
		return false;
	}
	
	@Override
	public Page<QuizEntity> findAllByUser(int page, int pageSize, String sortBy, UserEntity userEntity) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
		return quizRepository.findAllByUser(pageable, userEntity);
	}

}
