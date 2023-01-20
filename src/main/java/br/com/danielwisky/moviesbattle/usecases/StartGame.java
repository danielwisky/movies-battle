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

  /**
   * Starts a game with the provided id for the provided user and creates a new quiz.
   *
   * @param id   the id of the game that is being started
   * @param user the user for which the game is being started
   * @return the started game
   */
  public Game execute(final Long id, final User user) {
    final var game = findGame(id, user);
    validateStartPending(game);

    game.setStatus(STARTED);
    game.setLastModifiedDate(now());

    final var gameStarted = gameDataGateway.save(game);
    createQuiz.execute(gameStarted, user);
    return gameStarted;
  }

  private Game findGame(Long id, User user) {
    return gameDataGateway.findByIdAndUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
  }

  private void validateStartPending(final Game game) {
    if (!START_PENDING.equals(game.getStatus())) {
      throw new BusinessValidationException("O jogo não pode ser iniciado.");
    }
  }
}
