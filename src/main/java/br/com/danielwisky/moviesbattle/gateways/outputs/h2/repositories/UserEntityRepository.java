package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);
}