package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.FINISHED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EndGame {

  private final GameDataGateway gameDataGateway;
  private final CreateRanking createRanking;

  public Game execute(final Long id, final User user) {
    final var game = findGame(id, user);
    validateStartedGame(game);

    game.setStatus(FINISHED);
    game.setLastModifiedDate(now());

    final var gameSaved = gameDataGateway.save(game);
    createRanking.execute(gameSaved, user);
    return gameSaved;
  }

  private Game findGame(Long id, User user) {
    return gameDataGateway.findByIdAndUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
  }

  private void validateStartedGame(final Game game) {
    if (!STARTED.equals(game.getStatus())) {
      log.error("Game {} is not started and cannot be ended.", game.getId());
      throw new BusinessValidationException("O jogo não pode ser finalizado.");
    }
  }
}
