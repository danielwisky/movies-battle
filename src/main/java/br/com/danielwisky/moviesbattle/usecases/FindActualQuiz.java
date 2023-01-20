package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindActualQuiz {

  private final GameDataGateway gameDataGateway;
  private final QuizDataGateway quizDataGateway;

  /**
   * Finds the current quiz for the provided game and user.
   *
   * @param gameId the id of the game for which the current quiz is being searched
   * @param user   the user for which the current quiz is being searched
   * @return the current quiz for the provided game and user
   */
  public Quiz execute(final Long gameId, final User user) {
    final var game = findGame(gameId, user);
    validateStartedGame(game);

    return findActualQuiz(game);
  }

  private Quiz findActualQuiz(final Game game) {
    return quizDataGateway.findByGameAndStatus(game, NOT_ANSWERED)
        .orElseThrow(() -> new ResourceNotFoundException("Quiz atual não encontrado."));
  }

  private Game findGame(Long gameId, User user) {
    return gameDataGateway.findByIdAndUser(gameId, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
  }

  private void validateStartedGame(final Game game) {
    if (!STARTED.equals(game.getStatus())) {
      throw new BusinessValidationException("O jogo não está iniciado.");
    }
  }
}