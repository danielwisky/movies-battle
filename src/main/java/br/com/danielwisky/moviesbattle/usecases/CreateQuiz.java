package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Movie;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.gateways.outputs.MovieDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateQuiz {

  private static final int MAX_ATTEMPTS = 3;

  private final MovieDataGateway movieDataGateway;
  private final QuizDataGateway quizDataGateway;
  private final EndGame endGame;

  /**
   * Creates a new quiz with two randomly selected movies that haven't been used in a previous quiz
   * for the provided game. If no valid movies are found after the maximum number of attempts, the
   * end game process is triggered and an error message is returned.
   *
   * @param game the game for which the quiz is being created
   * @param user the user for which the quiz is being created
   * @return
   */
  public void execute(final Game game, final User user) {

    Movie movieOne;
    Movie movieTwo;
    int attempts = 0;
    boolean isValidMovies;

    do {
      final var randomMovies = movieDataGateway.findTwoRandomMovies();
      movieOne = randomMovies.get(INTEGER_ZERO);
      movieTwo = randomMovies.get(INTEGER_ONE);
      isValidMovies = !existsByGameAndMovies(game, movieOne, movieTwo);
      attempts++;
    } while (!isValidMovies && attempts < MAX_ATTEMPTS);

    if (isNull(movieOne) || isNull(movieTwo) || !isValidMovies) {
      endGame.execute(game.getId(), user);
      log.error("Failed to find new movies for game: {}", game.getId());
      throw new BusinessValidationException("Não foi possível encontrar novos filmes.");
    }

    final var newQuiz = Quiz.builder()
        .movieOne(movieOne)
        .movieTwo(movieTwo)
        .game(game)
        .status(NOT_ANSWERED)
        .lastModifiedDate(now())
        .build();

    quizDataGateway.save(newQuiz);
  }

  private boolean existsByGameAndMovies(final Game game, final Movie movieOne,
      final Movie movieTwo) {
    return quizDataGateway.existsByGameAndMovies(game, movieOne, movieTwo);
  }
}