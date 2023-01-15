package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_ONE;
import static br.com.danielwisky.moviesbattle.domains.enums.Answer.MOVIE_TWO;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.CORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.INCORRECT;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.QuizTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("AnswerQuiz Test Case")
class AnswerQuizTest extends UnitTest {

  @InjectMocks
  private AnswerQuiz answerQuiz;

  @Mock
  private GameDataGateway gameDataGateway;

  @Mock
  private QuizDataGateway quizDataGateway;

  @Mock
  private CreateQuiz createQuiz;

  @Mock
  private EndGame endGame;

  @Captor
  private ArgumentCaptor<Quiz> quizArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var user = UserTemplate.validUser();
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var quiz = QuizTemplate.validNotAnsweredWhenMovieOneIsCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));
    when(quizDataGateway.findByIdAndGame(quizId, game)).thenReturn(of(quiz));
    when(quizDataGateway.countByGameAndStatus(game, INCORRECT)).thenReturn(0l);

    answerQuiz.execute(gameId, quizId, MOVIE_ONE, user);

    verify(quizDataGateway).save(quizArgumentCaptor.capture());

    final var quizCaptured = quizArgumentCaptor.getValue();
    assertNotNull(quizCaptured);
    assertEquals(CORRECT, quizCaptured.getStatus());

    verify(createQuiz).execute(game, user);
    verifyNoInteractions(endGame);
  }

  @Test
  @DisplayName("should validate maximum number of attempts")
  void shouldValidateMaximumNumberOfAttempts() {
    final var user = UserTemplate.validUser();
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var quiz = QuizTemplate.validNotAnsweredWhenMovieOneIsCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));
    when(quizDataGateway.findByIdAndGame(quizId, game)).thenReturn(of(quiz));
    when(quizDataGateway.countByGameAndStatus(game, INCORRECT)).thenReturn(3l);

    answerQuiz.execute(gameId, quizId, MOVIE_TWO, user);

    verify(quizDataGateway).save(quizArgumentCaptor.capture());

    final var quizCaptured = quizArgumentCaptor.getValue();
    assertNotNull(quizCaptured);
    assertEquals(INCORRECT, quizCaptured.getStatus());

    verify(endGame).execute(game.getId(), user);
    verifyNoInteractions(createQuiz);
  }

  @Test
  @DisplayName("should throw exception when game not found")
  void shouldThrowExceptionWhenGameNotFound() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();
    final var quiz = QuizTemplate.validNotAnsweredWhenMovieOneIsCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> answerQuiz.execute(gameId, quizId, MOVIE_ONE, user));
  }

  @Test
  @DisplayName("should throw exception when quiz not found")
  void shouldThrowExceptionWhenQuizNotFound() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();
    final var quiz = QuizTemplate.validNotAnsweredWhenMovieOneIsCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));
    when(quizDataGateway.findByIdAndGame(quizId, game)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> answerQuiz.execute(gameId, quizId, MOVIE_ONE, user));
  }

  @Test
  @DisplayName("should throw exception when game has invalid status")
  void shouldThrowExceptionWhenGameHasInvalidStatus() {
    final var game = GameTemplate.validFinished();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();
    final var quiz = QuizTemplate.validNotAnsweredWhenMovieOneIsCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));

    assertThrows(BusinessValidationException.class,
        () -> answerQuiz.execute(gameId, quizId, MOVIE_ONE, user));
  }

  @Test
  @DisplayName("should throw exception when quiz has invalid status")
  void shouldThrowExceptionWhenQuizHasInvalidStatus() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();
    final var quiz = QuizTemplate.validCorrect();
    final var quizId = quiz.getId();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));
    when(quizDataGateway.findByIdAndGame(quizId, game)).thenReturn(of(quiz));

    assertThrows(BusinessValidationException.class,
        () -> answerQuiz.execute(gameId, quizId, MOVIE_ONE, user));
  }
}