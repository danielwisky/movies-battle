package br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories;

import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.QuizEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuizEntityRepository extends CrudRepository<QuizEntity, Long> {

}