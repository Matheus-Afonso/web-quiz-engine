package com.mth.webquiz.rest;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mth.webquiz.dto.AnswersDTO;
import com.mth.webquiz.dto.QuizDTO;
import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.service.QuizService;
import com.mth.webquiz.service.UserService;

@RestController
@RequestMapping("/api")
public class QuizController {
	private static final String NOT_FOUND_MESSAGE = "Quiz não encontrado";
	private static final String FORBIDDEN_MESSAGE = "Comando de deletar negado";
	
	@Autowired
	QuizService quizService;
	
	@Autowired
	UserService userService;
	
	// Map para GET {id} - Retorna o quiz pedido
	@GetMapping("/quizzes/{id}")
	public QuizDTO getQuiz(@PathVariable int id) {
		QuizEntity entity = quizService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
		return new QuizDTO(entity);
	}
	
	// Map para GET /quizzes - Retorna todos os quiz com ID
	@GetMapping("/quizzes")
	public Page<QuizDTO> getQuizzes(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy)
	{
		
		Page<QuizEntity> quizPage = quizService.findAll(page, pageSize, sortBy);
		return quizPage.map(QuizDTO::new);
	}
	
	// Map para POST /quizzes - Cria um novo quiz
	@PostMapping("/quizzes")
	public QuizDTO createQuiz(@Valid @RequestBody QuizDTO theQuiz, Errors errors, Authentication auth) {
		// Unico metodo de pegar o exception do Validate. ExceptionHandler não funciona
		if(errors.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalidFieldErrorMessage(errors));
		}
		QuizEntity quizEntity = new QuizEntity(theQuiz);
		
		quizEntity.setId(0);
		quizEntity.setUser(getCurrentUser(auth));
		quizService.save(quizEntity);
		
		return new QuizDTO(quizEntity);
	}
	
	// Map para POST quiz/id/solve - Recebe a resposta do usuário e retorna se acertou ou não
	@PostMapping("/quizzes/{quizId}/solve")
	public AnswerFeedback checkAnswer(@RequestBody AnswersDTO answer, @PathVariable int quizId) {
		QuizEntity quiz = quizService.findById(quizId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
		
		QuizDTO quizDTO = new QuizDTO(quiz);
		if (equalAnswers(quizDTO, answer)) {
			return new AnswerFeedback(true, "Parabéns! Você acertou!");
		} else {
			return new AnswerFeedback(false, "Resposta errada! Tente novamente.");
		}
	}
	
	// Map para DELETE quizzes/id - Deleta o quiz vinculado ao ID. Precisa de permissão de usuário
	@DeleteMapping("/quizzes/{quizId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)		// Retorna um 204 após deletar
	public void deleteQuiz(@PathVariable int quizId, Authentication auth) {
		try {
			boolean permission = quizService.deleteByIdAndUser(quizId, getCurrentUser(auth));
			if (!permission) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN_MESSAGE);
			}
		} catch (NoSuchElementException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
	}
	
	private UserEntity getCurrentUser(Authentication auth) {
		return userService.findByEmail(auth.getName()).
				orElseThrow(() -> new UsernameNotFoundException("Email nao encontrado"));
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
