package com.mth.webquiz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public List<QuizEntity> findByUser(UserEntity user) {
		return quizRepository.findByUser(user);
	}
	
	@Override
	public Optional<QuizEntity> findByIdAndUser(int id, UserEntity user) {
		return quizRepository.findByIdAndUser(id, user);
	}

	@Override
	public void deleteByIdAndUser(int quizId, UserEntity userEntity) {
		QuizEntity quiz = quizRepository.findById(quizId).orElseThrow(NoSuchElementException::new);
		if(quiz.getUser().getId() == userEntity.getId()) {
			quizRepository.deleteById(quizId);
		} else {
			throw new NoSuchElementException();
		}
	}

}
