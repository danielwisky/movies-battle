package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovieEntityRepository extends CrudRepository<MovieEntity, Long> {

}