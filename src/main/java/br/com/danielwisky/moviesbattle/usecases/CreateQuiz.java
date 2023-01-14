package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.gateways.outputs.MovieDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateQuiz {

  private final MovieDataGateway movieDataGateway;
  private final QuizDataGateway quizDataGateway;

  public void execute(final Game game) {

    final var randomMovies = movieDataGateway.findTwoRandomMovies();
    final var movieOne = randomMovies.get(INTEGER_ZERO);
    final var movieTwo = randomMovies.get(INTEGER_ONE);

    final var newQuiz = Quiz.builder()
        .movieOne(movieOne)
        .movieTwo(movieTwo)
        .game(game)
        .status(NOT_ANSWERED)
        .lastModifiedDate(now())
        .build();

    quizDataGateway.save(newQuiz);
  }
}