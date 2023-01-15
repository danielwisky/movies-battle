package br.com.danielwisky.moviesbattle.templates.domains;

import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.CORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.INCORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Quiz;

public class QuizTemplate {

  public static Quiz validNotAnswered() {
    return Quiz.builder()
        .id(1L)
        .movieOne(MovieTemplate.validMovieOne())
        .movieTwo(MovieTemplate.validMovieTwo())
        .status(NOT_ANSWERED)
        .lastModifiedDate(now())
        .build();
  }

  public static Quiz validCorrect() {
    return Quiz.builder()
        .id(2L)
        .movieOne(MovieTemplate.validMovieOne())
        .movieTwo(MovieTemplate.validMovieTwo())
        .status(CORRECT)
        .lastModifiedDate(now())
        .build();
  }

  public static Quiz validIncorrect() {
    return Quiz.builder()
        .id(3L)
        .movieOne(MovieTemplate.validMovieOne())
        .movieTwo(MovieTemplate.validMovieTwo())
        .status(INCORRECT)
        .lastModifiedDate(now())
        .build();
  }
}
