package br.com.danielwisky.moviesbattle.usecases;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGames {

  private final GameDataGateway gameDataGateway;

  public Page<Game> execute(final User user, final Pageable pageable) {
    return gameDataGateway.findAll(user, pageable);
  }
}