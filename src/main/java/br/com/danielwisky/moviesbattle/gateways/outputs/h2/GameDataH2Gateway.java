package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import static java.util.stream.Collectors.toSet;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.domains.enums.GameStatus;
import br.com.danielwisky.moviesbattle.gateways.outputs.GameDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.GameEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.UserEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.GameEntityRepository;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameDataH2Gateway implements GameDataGateway {

  private final GameEntityRepository repository;

  @Override
  public Game save(final Game game) {
    return repository.save(new GameEntity(game)).toDomain();
  }

  @Override
  public Optional<Game> findByIdAndUser(final Long id, final User user) {
    return repository.findByIdAndUser(id, new UserEntity(user)).map(GameEntity::toDomain);
  }

  @Override
  public Page<Game> findAllByUser(final User user, final Pageable pageable) {
    return repository.findAllByUser(new UserEntity(user), pageable).map(GameEntity::toDomain);
  }

  @Override
  public boolean existsByUserAndStatusIn(final User user, final Set<GameStatus> statuses) {
    return repository.existsByUserAndStatusIn(
        new UserEntity(user), statuses.stream().map(Enum::name).collect(toSet()));
  }
}