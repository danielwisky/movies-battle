package br.com.danielwisky.moviesbattle.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@DisplayName("FindGames Test Case")
class FindGamesTest extends UnitTest {

  @InjectMocks
  private FindGames findGames;

  @Mock
  private GameDataGateway gameDataGateway;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var pageable = PageRequest.of(0, 10);
    final var user = UserTemplate.validUser();
    final var games = List.of(GameTemplate.validStartPending());

    when(gameDataGateway.findAll(user, pageable)).thenReturn(new PageImpl<>(games));

    final var result = findGames.execute(user, pageable);
    assertNotNull(result);
  }
}