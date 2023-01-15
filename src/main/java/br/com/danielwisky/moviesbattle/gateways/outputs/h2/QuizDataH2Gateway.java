package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Movie;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.GameEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.MovieEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.QuizEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.QuizEntityRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizDataH2Gateway implements QuizDataGateway {

  private final QuizEntityRepository repository;

  @Override
  public Quiz save(final Quiz quiz) {
    return repository.save(new QuizEntity(quiz)).toDomain();
  }

  @Override
  public Optional<Quiz> findByGameAndStatus(final Game game, final QuizStatus status) {
    return repository.findByGameAndStatus(new GameEntity(game), status.name()).map(QuizEntity::toDomain);
  }

  @Override
  public Optional<Quiz> findByIdAndGame(final Long id, final Game game) {
    return repository.findByIdAndGame(id, new GameEntity(game)).map(QuizEntity::toDomain);
  }

  @Override
  public long countByGameAndStatus(final Game game, final QuizStatus status) {
    return repository.countByGameAndStatus(new GameEntity(game), status.name());
  }

  @Override
  public boolean existsByGameAndMovies(final Game game, final Movie movieOne, final Movie movieTwo) {
    return repository.existsByGameAndMovieOneAndMovieTwo(new GameEntity(game), new MovieEntity(movieOne), new MovieEntity(movieTwo))
        || repository.existsByGameAndMovieOneAndMovieTwo(new GameEntity(game), new MovieEntity(movieTwo), new MovieEntity(movieOne));
  }

  @Override
  public List<Quiz> findAllByGameAndStatusIn(final Game game, final List<QuizStatus> statuses) {
    return repository.findAllByGameAndStatusIn(new GameEntity(game), statuses.stream().map(Enum::name).toList())
        .stream()
        .map(QuizEntity::toDomain)
        .toList();
  }
}