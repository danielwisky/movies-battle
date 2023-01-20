package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.START_PENDING;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateGame {

  private final GameDataGateway gameDataGateway;

  /**
   * Creates a new game for the provided user.
   *
   * @param user the user for which the game is being created
   * @return the newly created game
   */
  public Game execute(final User user) {
    validateUser(user);
    final var newGame = Game.builder()
        .user(user)
        .status(START_PENDING)
        .lastModifiedDate(now())
        .build();
    return gameDataGateway.save(newGame);
  }

  private void validateUser(final User user) {
    if (gameDataGateway.existsByUserAndStatusIn(user, Set.of(START_PENDING, STARTED))) {
      log.error("User {} already has a game that hasn't been finished.", user.getId());
      throw new BusinessValidationException("Há um jogo que não foi finalizado.");
    }
  }
}