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

  /**
   * Finds all games for the provided user with pagination
   *
   * @param user     the user for which the games are being found
   * @param pageable the pagination information
   * @return a page of found games
   */
  public Page<Game> execute(final User user, final Pageable pageable) {
    return gameDataGateway.findAllByUser(user, pageable);
  }
}