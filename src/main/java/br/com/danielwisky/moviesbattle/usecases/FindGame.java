package br.com.danielwisky.moviesbattle.usecases;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGame {

  private final GameDataGateway gameDataGateway;

  public Game execute(final Long id, final User user) {
    return gameDataGateway.findByIdAndUser(id, user)
        .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));
  }
}
