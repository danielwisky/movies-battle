package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.START_PENDING;
import static java.time.LocalDateTime.now;
import static java.util.List.of;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateGame {

  private final GameDataGateway gameDataGateway;

  public Game execute(final User user) {
    validate(user);
    final var newGame = Game.builder()
        .user(user)
        .status(START_PENDING)
        .lastModifiedDate(now())
        .build();
    return gameDataGateway.save(newGame);
  }

  private void validate(final User user) {
    if (gameDataGateway.existsByUserAndStatusIn(user, of(START_PENDING, STARTED))) {
      throw new BusinessValidationException("Há um jogo que não foi finalizado.");
    }
  }
}