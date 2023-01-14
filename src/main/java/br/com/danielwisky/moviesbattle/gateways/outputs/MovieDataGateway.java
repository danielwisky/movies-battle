package br.com.danielwisky.moviesbattle.gateways.outputs;

import br.com.danielwisky.moviesbattle.domains.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieDataGateway {

  Optional<Movie> findById(Long id);

  List<Movie> findTwoRandomMovies();
}