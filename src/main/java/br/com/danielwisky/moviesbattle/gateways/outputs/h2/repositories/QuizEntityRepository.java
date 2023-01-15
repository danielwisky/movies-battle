package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.GameEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.MovieEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.QuizEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface QuizEntityRepository extends CrudRepository<QuizEntity, Long> {

  Optional<QuizEntity> findByGameAndStatus(GameEntity game, String status);

  Optional<QuizEntity> findByIdAndGame(Long id, GameEntity game);

  long countByGameAndStatus(GameEntity game, String status);

  boolean existsByGameAndMovieOneAndMovieTwo(
      GameEntity game, MovieEntity movieOne, MovieEntity movieTwo);

  List<QuizEntity> findAllByGameAndStatusIn(GameEntity game, List<String> statuses);
}