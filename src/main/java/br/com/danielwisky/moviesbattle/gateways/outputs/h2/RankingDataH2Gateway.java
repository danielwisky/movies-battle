package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import br.com.danielwisky.moviesbattle.domains.Ranking;
import br.com.danielwisky.moviesbattle.gateways.outputs.RankingDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.RankingEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.RankingEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankingDataH2Gateway implements RankingDataGateway {

  private final RankingEntityRepository repository;

  @Override
  public Ranking save(Ranking ranking) {
    return repository.save(new RankingEntity(ranking)).toDomain();
  }

  @Override
  public Page<Ranking> findAll(final Pageable pageable) {
    return repository.findAll(pageable).map(RankingEntity::toDomain);
  }
}