package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.QuizEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface QuizEntityRepository extends CrudRepository<QuizEntity, Long> {

  Optional<QuizEntity> findByGameIdAndStatus(Long id, String status);

  Optional<QuizEntity> findByIdAndGameId(Long id, Long gameId);

  long countByGameIdAndStatus(Long id, String status);
}