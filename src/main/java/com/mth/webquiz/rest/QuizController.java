package com.mth.webquiz.rest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.mth.webquiz.dto.SolvedTimeDTO;
import com.mth.webquiz.dto.UserDTO;
import com.mth.webquiz.entity.QuizEntity;
import com.mth.webquiz.entity.SolvedTimeEntity;
import com.mth.webquiz.entity.UserEntity;
import com.mth.webquiz.service.QuizService;
import com.mth.webquiz.service.SolvedTimeService;

@RestController
@RequestMapping("/api")
public class QuizController {
	private static final String NOT_FOUND_MESSAGE = "Quiz não encontrado";
	private static final String FORBIDDEN_MESSAGE = "Usuário não possui permissão";
	private static final String CORRECT_ANSWER_MESSAGE = "Parabéns! Você acertou!";
	private static final String WRONG_ANSWER_MESSAGE = "Resposta errada! Tente novamente.";
	private static final String BAD_REQUEST_MESSAGE = "Parâmetros inválidos";
	
	@Autowired
	QuizService quizService;
	
	@Autowired
	SolvedTimeService timeService;
	
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
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort)
	{
		
		Page<QuizEntity> quizPage = quizService.findAll(page, size, sort);
		return quizPage.map(QuizDTO::new);
	}

	// Map para GET /api/quizzes/completed
	@GetMapping("/quizzes/completed")
	public Page<SolvedTimeDTO> getTimestamps(@AuthenticationPrincipal UserDTO user,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "primaryId") String sort)
	{
		Page<SolvedTimeEntity> timestamps = 
				timeService.findAllbyUser(page, size, sort, new UserEntity(user));
		return timestamps.map(SolvedTimeDTO::new);
	}
	
	// Map para POST /quizzes - Cria um novo quiz
	@PostMapping("/quizzes")
	public QuizDTO createQuiz(@Valid @RequestBody QuizDTO theQuiz, Errors errors, 
			@AuthenticationPrincipal UserDTO user) {

		if(errors.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, invalidFieldErrorMessage(errors));
		}
		QuizEntity quizEntity = new QuizEntity(theQuiz);
		quizEntity.setUser(new UserEntity(user));
		quizService.save(quizEntity);
		
		return new QuizDTO(quizEntity);
	}
	
	// Map para POST quiz/id/solve - Recebe a resposta do usuário e retorna se acertou ou não
	@PostMapping("/quizzes/{quizId}/solve")
	public Map<String, Object> checkAnswer(@RequestBody AnswersDTO answer, @PathVariable int quizId, 
			@AuthenticationPrincipal UserDTO user) {
		QuizEntity quiz = quizService.findById(quizId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));

		QuizDTO quizDTO = new QuizDTO(quiz);
		if (equalAnswers(quizDTO, answer)) {
			timeService.save(new SolvedTimeEntity(quizId, getCurrentTime(), new UserEntity(user)));
			return Map.of("success", true, 
					"feedback", CORRECT_ANSWER_MESSAGE);
		} else {
			return Map.of("success", false, 
					"feedback", WRONG_ANSWER_MESSAGE);
		}
	}
	
	// Map para DELETE quizzes/id - Deleta o quiz vinculado ao ID do dono.
	@DeleteMapping("/quizzes/{quizId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)		// Retorna um 204 após deletar
	public void deleteQuiz(@PathVariable int quizId, @AuthenticationPrincipal UserDTO user) {
		try {
			boolean permission = quizService.deleteByIdAndUser(quizId, new UserEntity(user));
			if (!permission) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN_MESSAGE);
			}
		} catch (NoSuchElementException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
	}
	
	// Map para PATCH quizzes/id - Atualiza o quiz vinculado ao ID do dono
	@PatchMapping("/quizzes/{quizId}")
	public QuizDTO updateQuiz(@PathVariable int quizId, @RequestBody Map<String, Object> fields, 
			@AuthenticationPrincipal UserDTO user) {
		// Checagem dos parâmetros passados no Map
		if (!isFieldValid(fields)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
		}
		
		// Checa se existe e se o usuário editando é o dono
		QuizEntity entity = quizService.findById(quizId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE));
		
		if (entity.getUser().getId() != user.getId()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN_MESSAGE);
		}
		
		// Caso tenha passado um campo "id" no body, deletá-lo já que isso não será mudado
		fields.remove("id");
		
		QuizDTO updatedDto = new QuizDTO(entity);
		
		try {
			fields.forEach((k, v) -> {
				// Usando reflection para pegar o parâmetro de nome "v" no DTO e salvar o valor "k" nele
				Field field = ReflectionUtils.findField(QuizDTO.class, k);
				field.setAccessible(true);
				ReflectionUtils.setField(field, updatedDto, v);
			});
		// Filtrar campos. Para o caso de passar um tipo inválido en um campo
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
		}
		
		QuizEntity updatedEntity = new QuizEntity(updatedDto);
		updatedEntity.setUser(new UserEntity(user));
		quizService.save(updatedEntity);
		
		return updatedDto;
	}

	//Mapa para GET quizzes/myquizzes - Mostra todos os quizzes vinculados ao ID do dono
	@GetMapping("/quizzes/myquizzes")
	public Page<QuizDTO> getUserQuizzes(@AuthenticationPrincipal UserDTO userDTO, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort ) {
		
		Page<QuizEntity> userQuizzes = quizService.findAllByUser(page, size, sort, new UserEntity(userDTO));
		return userQuizzes.map(QuizDTO::new);
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
	
	private String getCurrentTime() {
		return LocalDateTime.now().toString();
	}
	
	private boolean isFieldValid(Map<String, Object> fields) {
		final String optionsField = "option";
		if (fields == null || fields.isEmpty()) {
			return false;
		}
		
		// Verificar os campos title e text. Opcionais
		for (Object value: fields.values()) {
			if (value instanceof String && ((String) value).isEmpty()) {
				return false;
			}
		}
		
		// Verificar campo options. Opcional
		if (fields.get(optionsField) == null) {
			return true;
		}
		
		return (fields.get(optionsField) instanceof List) &&
				((List<?>) fields.get(optionsField)).size() > 1;
	}
}
