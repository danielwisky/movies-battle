package br.com.danielwisky.moviesbattle.gateways.outputs;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Movie;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
import java.util.List;
import java.util.Optional;

public interface QuizDataGateway {

  Quiz save(Quiz quiz);

  Optional<Quiz> findByGameAndStatus(Game game, QuizStatus status);

  Optional<Quiz> findByIdAndGame(Long id, Game game);

  long countByGameAndStatus(Game game, QuizStatus status);

  boolean existsByGameAndMovies(Game game, Movie movieOne, Movie movieTwo);

  List<Quiz> findAllByGameAndStatusIn(Game game, List<QuizStatus> statuses);
}