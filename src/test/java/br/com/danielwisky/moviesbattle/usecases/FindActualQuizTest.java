package br.com.danielwisky.moviesbattle.usecases;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.QuizTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("FindActualQuiz Test Case")
class FindActualQuizTest extends UnitTest {

  @InjectMocks
  private FindActualQuiz findActualQuiz;

  @Mock
  private GameDataGateway gameDataGateway;

  @Mock
  private QuizDataGateway quizDataGateway;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();
    final var quiz = QuizTemplate.validNotAnswered();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));
    when(quizDataGateway.findByGameAndStatus(game, QuizStatus.NOT_ANSWERED)).thenReturn(of(quiz));

    final var result = findActualQuiz.execute(gameId, user);
    assertNotNull(result);
  }

  @Test
  @DisplayName("should throw exception when invalid status")
  void shouldThrowExceptionWhenInvalidStatus() {
    final var game = GameTemplate.validFinished();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));

    assertThrows(BusinessValidationException.class, () -> findActualQuiz.execute(gameId, user));
  }

  @Test
  @DisplayName("should throw exception when game not found")
  void shouldThrowExceptionWhenGameNotFound() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class, () -> findActualQuiz.execute(gameId, user));
  }

  @Test
  @DisplayName("should throw exception when quiz not found")
  void shouldThrowExceptionWhenQuizNotFound() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(of(game));

    when(quizDataGateway.findByGameAndStatus(game, QuizStatus.NOT_ANSWERED)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class, () -> findActualQuiz.execute(gameId, user));
  }
}