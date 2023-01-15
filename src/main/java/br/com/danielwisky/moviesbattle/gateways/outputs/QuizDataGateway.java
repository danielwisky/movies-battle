package br.com.danielwisky.moviesbattle.gateways.outputs;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
import java.util.Optional;

public interface QuizDataGateway {

  Quiz save(Quiz quiz);

  Optional<Quiz> findByGameAndStatus(Game game, QuizStatus status);

  Optional<Quiz> findByIdAndGame(Long id, Game game);

  long countByGameAndStatus(Game game, QuizStatus status);
}