package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.START_PENDING;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("CreateGame Test Case")
class CreateGameTest extends UnitTest {

  @InjectMocks
  private CreateGame createGame;

  @Mock
  private GameDataGateway gameDataGateway;

  @Captor
  private ArgumentCaptor<Game> gameArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var user = UserTemplate.validUser();

    when(gameDataGateway.existsByUserAndStatusIn(user, of(START_PENDING, STARTED))).thenReturn(
        false);

    createGame.execute(user);

    verify(gameDataGateway).save(gameArgumentCaptor.capture());

    final var gameCaptured = gameArgumentCaptor.getValue();

    assertNotNull(gameCaptured);
    assertEquals(START_PENDING, gameCaptured.getStatus());
  }

  @Test
  @DisplayName("should throw exception when there is a game in progress")
  void shouldThrowExceptionWhenThereIsAGameInProgress() {
    final var user = UserTemplate.validUser();
    when(gameDataGateway.existsByUserAndStatusIn(user, of(START_PENDING, STARTED))).thenReturn(
        true);
    assertThrows(BusinessValidationException.class, () -> createGame.execute(user));
  }
}