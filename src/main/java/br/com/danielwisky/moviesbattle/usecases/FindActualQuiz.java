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

  public Quiz execute(final Long gameId, final User user) {
    final var gameData = gameDataGateway.findByIdAndUser(gameId, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
    validateGameStarted(gameData);

    return quizDataGateway.findByGameAndStatus(gameData, NOT_ANSWERED)
        .orElseThrow(() -> new ResourceNotFoundException("Quiz atual não encontrado."));
  }

  private void validateGameStarted(final Game game) {
    if (!STARTED.equals(game.getStatus())) {
      throw new BusinessValidationException("O jogo não está iniciado.");
    }
  }
}