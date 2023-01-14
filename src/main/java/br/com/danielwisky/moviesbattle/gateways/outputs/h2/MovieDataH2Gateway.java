package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import br.com.danielwisky.moviesbattle.domains.Movie;
import br.com.danielwisky.moviesbattle.gateways.outputs.MovieDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.MovieEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.MovieEntityRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieDataH2Gateway implements MovieDataGateway {

  private final MovieEntityRepository repository;

  @Override
  public Optional<Movie> findById(final Long id) {
    return repository.findById(id).map(MovieEntity::toDomain);
  }

  @Override
  public List<Movie> findTwoRandomMovies() {
    return repository.findTwoRandomMovies()
        .stream()
        .map(MovieEntity::toDomain)
        .toList();
  }
}