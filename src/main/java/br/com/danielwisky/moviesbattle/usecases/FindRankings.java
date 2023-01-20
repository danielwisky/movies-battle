package br.com.danielwisky.moviesbattle.usecases;

import br.com.danielwisky.moviesbattle.domains.Ranking;
import br.com.danielwisky.moviesbattle.gateways.outputs.RankingDataGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindRankings {

  private final RankingDataGateway rankingDataGateway;

  /**
   * Finds all rankings with pagination
   *
   * @param pageable the pagination information
   * @return a page of found rankings
   */
  public Page<Ranking> execute(final Pageable pageable) {
    return rankingDataGateway.findAll(pageable);
  }
}
