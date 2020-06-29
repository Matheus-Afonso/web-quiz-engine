# Web Quiz Engine
A Web Quiz Engine é uma API RESTful que armazena quizzes, ou seja, perguntas com múltiplas opções. Essa API suporta cadastro de usuários, 
ferramentas CRUD para quizzes, receber respostas do usuário para validá-las e salvar um registro de quando um usuário acertar a resposta do quiz.

Feito de acordo com o projeto [Web Quiz Engine](https://hyperskill.org/projects/91) do Hyperskill.

A API foi feita usando a linguagem **Java** e framework **Spring Boot**. Módulos e frameworks usados:
- **Gradle**.
- **Spring Security** para cadastro e autenticação de usuário e senha.
- **Hibernate e Spring Data JPA** para validação de dados e acesso ao banco de dados.
- **H2 Database** como banco de dados portátil.

### Dependências
Dependência | Descrição | Requisições
----------- | --------- | ---------------
```/api/register``` | Cadastro de usuários | POST
```/api/quizzes``` | Ver quizzes cadastrados ou criar um novo | GET, POST
```/api/quizzes/{id}``` | Ver, editar ou deletar um quiz com id específico | GET, PATCH, DELETE
```/api/quizzes/{id}/solve``` | Recebe a resposta do usuário | POST
```/api/quizzes/completed``` | Hora e data dos acertos do usuário atual | GET

## Instruções

### Compilação
Para subir a API, utilizar o comando para executar projetos Spring Boot no Gradle. Windows: ```gradlew bootRun```, Linux: ```./gradlew bootRun```. 
Não é necessária nenhuma configuração adicional da DB já que todas as configurações para a H2 Database já estão feitas em [application.properties](src/main/resources/application.properties).

### Cadastro de Usuário
Para usar as funções da API, é necessário um usuário cadastrado, senão qualquer comando feito em /api/quizzes irá receber um ```405 Unauthorized``` como resposta.
Para o cadastrar um novo usuário:

```POST /api/register```

Body:
```
{
  "email": "email@example.com",
  "password": "senha"
}
```

Parâmetro | Tipo | Descrição
--------- | ---- | ---------
email | String | Email para ser usado no login. **Precisa possuir um "@" e um "."**
password | String | Senha para login. **Tamanho mínimo de 5 caracteres**

Se estiver tudo certo, retornará ```200 OK```, caso contrário, retornará ```400 Bad Request``` com o erro descrito no parâmetro message.
Ao ter um usuário cadastrado, é necessário usar as informações de login para todas as requisições em api/quizzes. 
Não é possível ver, editar ou deletar usuários pela API. Para essas tarefas acessar o /h2-console e logar com o usuário e senha salvos em [application.properties](src/main/resources/application.properties).

### Cadastrar Quiz
```POST /api/quizzes``` 

Exemplo de Body:
```
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": [2]
}
```
Parâmetro | Tipo | Descrição
--------- | ---- | ---------
title | String | Título do quiz. **Obrigatório**
text | String | Enunciado do quiz. **Obrigatório**
options | List | Opções disponíveis. **No mínimo 2 elementos**
answer | List | Número(s) da opção(ões) correta(s) considerando que a primeira opção seja 0. **Opcional**, logo podem existir quizzes sem resposta certa

Se estiver tudo certo, retornará ```200 OK``` com um body indicando os dados do quiz que foram passados (sem o parâmetro "answer") e o id atribuído a ele. Caso ocorra um erro,
retornará ```400 Bad Request``` com o erro descrito no parâmetro message.

### Ver Quiz
Para ver um quiz específico pelo seu ID, usar:

```GET /api/quizzes/{id}```

Se o quiz relacionado ao ID passado existir, retornará ```200 OK``` com um body indicando os dados do quiz sem o parâmetro "answer". Caso contrário retonará 
```404 Not Found```.

Para ver todos os quizzes cadastrados na DB, usar:

```GET /api/quizzes```

Isso retornará um body com todos os quizzes no parâmetro "content" por estar em padrão de paginação. Por padrão aparecem 10 quizzes por página organizados 
em ordem crescente pelo ID. Alguns exemplos de comandos usando a paginação.
- ```GET /api/quizzes?size=2&page=3``` - Limita 2 itens por página e retorna a terceira página.
- ```GET /api/quizzes?sort=title``` - Organiza por título em ordem crescente.

### Deletar Quiz
**Apenas o usuário que criou o quiz em específico pode deletá-lo**. Para deletar um quiz pelo seu ID, usar:

```DELETE /api/quizzes/{id}```

Caso o quiz exista e o usuário atual seja o criador do quiz, a API retornará ```204 No Content```. Caso contrário, poderá retornar ```403 Forbidden``` 
ou ```404 Not Found```.
