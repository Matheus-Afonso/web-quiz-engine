package com.mth.webquiz;

/* PARTES:
 * 1 - Retorna um mesmo JSON com a pergunta usando um Rest Controller
 * 2 - Possibilidade de criar quizzes, ver todos, pegar um pelo id e resolver pelo id
 * 3 - Varios:
 * Checar a criação de quizzes se todos os campos estiverem corretos
 * Possibilidade de criar perguntas com múltiplas respostas
 * A resposta deve ser um array em um JSON
 * Retornar um JSON customizado de erro (na vdd só precisa do código, mas customize para melhorar)
 * 4 - Integração com DB H2 Database. Tres tabelas: Quiz, Opções de cada quiz, Respostas de cada quiz
 * 5 - Adicionar Spring Security + várias coisas:
 * Deletar um quiz
 * Criar um novo usuário via POST em api/register. Usuario e senha(com BCrypt)
 * Todas as operações precisam dos dados do usuário
 * Só pode mostrar os quiz relacionados ao usuário
 * */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HyperskillWebquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyperskillWebquizApplication.class, args);
	}

}
