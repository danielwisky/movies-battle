package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.MovieEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieEntityRepository extends CrudRepository<MovieEntity, Long> {

  @Query(value = "SELECT * FROM MOVIES ORDER BY RANDOM() LIMIT 2", nativeQuery = true)
  List<MovieEntity> findTwoRandomMovies();
}