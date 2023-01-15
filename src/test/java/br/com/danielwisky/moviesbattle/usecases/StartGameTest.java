package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("StartGame Test Case")
class StartGameTest extends UnitTest {

  @InjectMocks
  private StartGame startGame;

  @Mock
  private GameDataGateway gameDataGateway;

  @Mock
  private CreateQuiz createQuiz;

  @Captor
  private ArgumentCaptor<Game> gameArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStartPending();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(game.getId(), user)).thenReturn(Optional.of(game));
    when(gameDataGateway.save(game)).thenReturn(game);

    startGame.execute(game.getId(), user);

    verify(createQuiz).execute(game, user);
    verify(gameDataGateway).save(gameArgumentCaptor.capture());

    final var gameCaptured = gameArgumentCaptor.getValue();

    assertNotNull(gameCaptured);
    assertEquals(STARTED, gameCaptured.getStatus());
  }

  @Test
  @DisplayName("should throw exception when game not found")
  void shouldThrowExceptionWhenGameNotFound() {
    final var game = GameTemplate.validFinished();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(game.getId(), user)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class, () -> startGame.execute(game.getId(), user));
  }

  @Test
  @DisplayName("should throw exception when invalid status")
  void shouldThrowExceptionWhenInvalidStatus() {
    final var game = GameTemplate.validFinished();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(game.getId(), user)).thenReturn(Optional.of(game));

    assertThrows(BusinessValidationException.class, () -> startGame.execute(game.getId(), user));
  }
}