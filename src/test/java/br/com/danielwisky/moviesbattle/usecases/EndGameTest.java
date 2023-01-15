package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.FINISHED;
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

@DisplayName("EndGame Test Case")
class EndGameTest extends UnitTest {

  @InjectMocks
  private EndGame endGame;

  @Mock
  private GameDataGateway gameDataGateway;

  @Mock
  private CreateRanking createRanking;

  @Captor
  private ArgumentCaptor<Game> gameArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(Optional.of(game));
    when(gameDataGateway.save(game)).thenReturn(game);

    endGame.execute(gameId, user);

    verify(createRanking).execute(game, user);
    verify(gameDataGateway).save(gameArgumentCaptor.capture());

    final var gameCaptured = gameArgumentCaptor.getValue();

    assertNotNull(gameCaptured);
    assertEquals(FINISHED, gameCaptured.getStatus());
  }

  @Test
  @DisplayName("should throw exception when game not found")
  void shouldThrowExceptionWhenGameNotFound() {
    final var game = GameTemplate.validStarted();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class, () -> endGame.execute(gameId, user));
  }

  @Test
  @DisplayName("should throw exception when invalid status")
  void shouldThrowExceptionWhenInvalidStatus() {
    final var game = GameTemplate.validFinished();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(Optional.of(game));

    assertThrows(BusinessValidationException.class, () -> endGame.execute(gameId, user));
  }
}