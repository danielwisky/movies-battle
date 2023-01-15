package br.com.danielwisky.moviesbattle.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("FindRankings Test Case")
class FindGameTest extends UnitTest {

  @InjectMocks
  private FindGame findGame;

  @Mock
  private GameDataGateway gameDataGateway;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStartPending();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(game.getId(), user)).thenReturn(Optional.of(game));

    final var result = findGame.execute(game.getId(), user);
    assertNotNull(result);
  }

  @Test
  @DisplayName("should throw exception when game not found")
  void shouldThrowExceptionWhenGameNotFound() {
    final var game = GameTemplate.validStartPending();
    final var gameId = game.getId();
    final var user = UserTemplate.validUser();

    when(gameDataGateway.findByIdAndUser(gameId, user)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> findGame.execute(gameId, user));
  }
}