package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.QuizEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.QuizEntityRepository;
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
    return repository.findByGameIdAndStatus(game.getId(), status.name()).map(QuizEntity::toDomain);
  }
}