package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_ONE;
import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_TWO;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.CORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.INCORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Movie;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.enums.Answer;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerQuiz {

  private static final long LONG_THREE = 3;

  private final GameDataGateway gameDataGateway;
  private final QuizDataGateway quizDataGateway;
  private final CreateQuiz createQuiz;
  private final EndGame endGame;

  public Quiz execute(final Long gameId, final Long quizId, final Answer answer, final User user) {
    final var gameData = gameDataGateway.findByIdAndUser(gameId, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
    validateStartedGame(gameData);

    final var quizData = quizDataGateway.findByIdAndGame(quizId, gameData)
        .orElseThrow(() -> new ResourceNotFoundException("Quiz não encontrado."));
    validateNotAnsweredQuiz(quizData);

    final var isCorrect = isCorrect(answer, quizData.getMovieOne(), quizData.getMovieTwo());
    quizData.setStatus(isCorrect ? CORRECT : INCORRECT);
    quizData.setLastModifiedDate(now());

    final var quizUpdated = quizDataGateway.save(quizData);

    validateAttempts(gameData, user);

    return quizUpdated;
  }

  private void validateAttempts(final Game game, final User user) {
    final var totalIncorrectAnswers = quizDataGateway.countByGameAndStatus(game, INCORRECT);
    if (totalIncorrectAnswers >= LONG_THREE) {
      endGame.execute(game.getId(), user);
    } else {
      createQuiz.execute(game, user);
    }
  }

  private boolean isCorrect(final Answer answer, final Movie movieOne, final Movie movieTwo) {
    return (MOVIE_ONE.equals(answer) && movieOne.isBetterRatedThan(movieTwo))
        || (MOVIE_TWO.equals(answer) && movieTwo.isBetterRatedThan(movieOne));
  }

  private void validateNotAnsweredQuiz(final Quiz quiz) {
    if (!NOT_ANSWERED.equals(quiz.getStatus())) {
      throw new BusinessValidationException("Quiz já respondido.");
    }
  }

  private void validateStartedGame(final Game game) {
    if (!STARTED.equals(game.getStatus())) {
      throw new BusinessValidationException("O jogo não está iniciado.");
    }
  }
}
