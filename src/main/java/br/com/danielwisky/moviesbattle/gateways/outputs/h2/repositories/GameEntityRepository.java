package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.GameEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GameEntityRepository extends CrudRepository<GameEntity, Long> {

  Optional<GameEntity> findByIdAndUserId(Long id, Long userId);

  Page<GameEntity> findAllByUserId(Long userId, Pageable pageable);

  boolean existsByUserIdAndStatusIn(Long userId, List<String> statuses);

}