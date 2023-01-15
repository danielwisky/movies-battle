package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.GameEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GameEntityRepository extends CrudRepository<GameEntity, Long> {

  Optional<GameEntity> findByIdAndUser(Long id, UserEntity user);

  Page<GameEntity> findAllByUser(UserEntity user, Pageable pageable);

  boolean existsByUserAndStatusIn(UserEntity user, List<String> statuses);
}