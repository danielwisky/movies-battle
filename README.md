# Movies Battle

API REST para uma aplicação ao estilo card game, onde serão informados dois filmes e o jogador deve acertar aquele que possui melhor avaliação no IMDB.

## Tecnologias

- JDK 17
- Spring Boot
- Spring Data
- Spring Security
- H2
- OpenAPI

## Rodando localmente

Clone o projeto:

```bash
  git clone git@github.com:danielwisky/movies-battle.git
```

Entre no diretório do projeto:

```bash
  cd movies-battle
```

Instale as dependências:

```bash
  mvn clean install
```

Inicie o servidor:

```bash
  mvn spring-boot:run
```

Usuário/senha carregados automaticamente na base de dados H2:

| usuário  | senha |
| -------- | ----- |
| admin    | senha |
| daniel   | senha |
| isabella | senha |

## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn clean test
```

## Documentação da API

- [Swagger](http://localhost:8080/swagger-ui.html)
- [API Docs](http://localhost:8080/v3/api-docs)

## Autores

- [@danielwisky](https://www.github.com/danielwisky)
