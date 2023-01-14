package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.START_PENDING;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartGame {

  private final GameDataGateway gameDataGateway;
  private final CreateQuiz createQuiz;

  public Game execute(final Long id, final User user) {
    final var gameData = gameDataGateway.findByIdAndUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

    validate(gameData);

    gameData.setStatus(STARTED);
    gameData.setLastModifiedDate(now());

    final var gameStarted = gameDataGateway.save(gameData);
    createQuiz.execute(gameStarted);
    return gameStarted;
  }

  private void validate(final Game game) {
    if (!START_PENDING.equals(game.getStatus())) {
      throw new BusinessValidationException("O jogo não pode ser iniciado.");
    }
  }
}
