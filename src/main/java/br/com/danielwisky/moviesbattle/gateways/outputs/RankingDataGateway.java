package br.com.danielwisky.moviesbattle.gateways.outputs;

import br.com.danielwisky.moviesbattle.domains.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingDataGateway {

  Ranking save(Ranking ranking);

  Page<Ranking> findAll(Pageable pageable);
}
