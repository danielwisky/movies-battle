package br.com.danielwisky.moviesbattle.gateways.outputs;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.enums.GameStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameDataGateway {

  Game save(Game game);

  Optional<Game> findByIdAndUser(Long id, User user);

  Page<Game> findAll(User user, Pageable pageable);

  boolean existsByUserAndStatusIn(User user, List<GameStatus> statuses);

}
