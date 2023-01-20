package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_ONE;
import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_TWO;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.CORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.INCORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
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

  private static final long MAX_INCORRECT_ANSWERS = 3;

  private final GameDataGateway gameDataGateway;
  private final QuizDataGateway quizDataGateway;
  private final CreateQuiz createQuiz;
  private final EndGame endGame;

  /**
   * Answers the quiz with the provided answer.
   *
   * @param gameId the id of the game to which the quiz belongs
   * @param quizId the id of the quiz being answered
   * @param answer the answer provided by the user
   * @param user   the user who is answering the quiz
   * @return the quiz with the updated answer and status
   */
  public Quiz execute(final Long gameId, final Long quizId, final Answer answer, final User user) {
    final var game = findGame(gameId, user);
    validateStartedGame(game);
    final var quiz = findQuiz(quizId, game);
    validateNotAnsweredQuiz(quiz);

    final var isCorrect = isCorrectAnswer(answer, quiz);
    quiz.setStatus(isCorrect ? CORRECT : INCORRECT);
    quiz.setLastModifiedDate(now());

    final var quizUpdated = quizDataGateway.save(quiz);
    checkQuizAttempts(game, user);
    return quizUpdated;
  }

  private Quiz findQuiz(final Long quizId, final Game game) {
    return quizDataGateway.findByIdAndGame(quizId, game)
        .orElseThrow(() -> new ResourceNotFoundException("Quiz não encontrado."));
  }

  private Game findGame(final Long gameId, final User user) {
    return gameDataGateway.findByIdAndUser(gameId, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
  }

  private void checkQuizAttempts(final Game game, final User user) {
    final var totalIncorrectAnswers = quizDataGateway.countByGameAndStatus(game, INCORRECT);
    if (totalIncorrectAnswers >= MAX_INCORRECT_ANSWERS) {
      endGame.execute(game.getId(), user);
    } else {
      createQuiz.execute(game, user);
    }
  }

  private boolean isCorrectAnswer(final Answer answer, final Quiz quiz) {
    return (MOVIE_ONE.equals(answer) && quiz.getMovieOne().isBetterRatedThan(quiz.getMovieTwo()))
        || (MOVIE_TWO.equals(answer) && quiz.getMovieTwo().isBetterRatedThan(quiz.getMovieOne()));
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
