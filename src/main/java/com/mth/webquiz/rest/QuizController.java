package com.mth.webquiz.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mth.webquiz.dto.AnswersDTO;
import com.mth.webquiz.dto.QuizDTO;
import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.service.QuizService;

@RestController
@RequestMapping("/api")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	// Map para GET {id} - Retorna o quiz pedido
	@GetMapping("/quizzes/{id}")
	public QuizDTO getQuiz(@PathVariable int id) {
		QuizEntity entity = quizService.findById(id)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz não encontrado"));
		
		return new QuizDTO(entity);
	}
	
	// Map para GET /quizzes - Retorna todos os quiz com ID
	@GetMapping("/quizzes")
	public List<QuizDTO> getQuizzes() {
		List<QuizEntity> quizEntities = quizService.findAll();
		
		return quizEntities.stream()
							.map(QuizDTO::new)
							.collect(Collectors.toList());
	}
	
	// Map para POST /quizzes - Cria um novo quiz
	@PostMapping("/quizzes")
	public QuizDTO createQuiz(@Valid @RequestBody QuizDTO theQuiz, Errors errors) {
		// Unico metodo de pegar o exception do Validate. ExceptionHandler não funciona
		if(errors.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalidFieldErrorMessage(errors));
		}
		
		QuizEntity quizEntity = new QuizEntity(theQuiz);
		
		quizEntity.setId(0);
		quizService.save(quizEntity);
		
		return new QuizDTO(quizEntity);
	}
	
	// Map para POST quiz/id/solve - Recebe a resposta do usuário e retorna se acertou ou não
	@PostMapping("/quizzes/{quizId}/solve")
	public AnswerFeedback checkAnswer(@RequestBody AnswersDTO answer, @PathVariable int quizId) {
		QuizEntity quiz = quizService.findById(quizId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz não encontrado"));
		
		QuizDTO quizDTO = new QuizDTO(quiz);
		if (equalAnswers(quizDTO, answer)) {
			return new AnswerFeedback(true, "Parabéns! Você acertou!");
		} else {
			return new AnswerFeedback(false, "Resposta errada! Tente novamente.");
		}
	}
	
	// Map para DELETE quizzes/id - Deleta o quiz vinculado ao ID
	@DeleteMapping("/quizzes/{quizId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)		// Retorna um 204 após deletar
	public void deleteQuiz(@PathVariable int quizId) {
		// TODO: Checar se o usuário pegou um ID válido
		try {
			quizService.deleteById(quizId);
		} catch (EmptyResultDataAccessException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz não encontrado");
		}
	}
	
	private String invalidFieldErrorMessage(Errors errors) {
		StringBuilder msg = new StringBuilder();
		msg.append(errors.getFieldError().getField())
			.append(": ")
			.append(errors.getFieldError().getDefaultMessage());
		
		return msg.toString();
	}
	
	private boolean equalAnswers(QuizDTO quizDTO, AnswersDTO answer) {
		List<Integer> correctOpts = quizDTO.getAnswer();
		List<Integer> answeredOpts = answer.getAnswer();
		// Caso não possua respostas certas
		
		if(correctOpts == null) {
			return answeredOpts.isEmpty();
		}
		
		// Ordem dos itens na lista não importa
		if (answeredOpts != null && correctOpts.size() == answeredOpts.size()) {
			Collections.sort(correctOpts);
			Collections.sort(answeredOpts);
			return answeredOpts.equals(correctOpts);
		}
		
		return false;
	}
	
}
