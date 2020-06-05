package com.mth.webquiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mth.webquiz.dao.QuizRepository;
import com.mth.webquiz.entity.QuizEntity;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	QuizRepository quizRepository;
	
	@Override
	public void save(QuizEntity theQuiz) {
		quizRepository.save(theQuiz);
	}

	@Override
	public List<QuizEntity> findAll() {
		return quizRepository.findAll();
	}

	@Override
	public Optional<QuizEntity> findById(int id) {
		return quizRepository.findById(id);
	}

	@Override
	public void deleteById(int id) {
		quizRepository.deleteById(id);
	}

}
