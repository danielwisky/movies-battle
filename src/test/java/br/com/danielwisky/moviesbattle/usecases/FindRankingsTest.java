package br.com.danielwisky.moviesbattle.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.gateways.outputs.RankingDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.RankingTemplate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@DisplayName("FindRankings Test Case")
class FindRankingsTest extends UnitTest {

  @InjectMocks
  private FindRankings findRankings;

  @Mock
  private RankingDataGateway rankingDataGateway;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var pageable = PageRequest.of(0, 10);
    final var rankings = List.of(RankingTemplate.valid());

    when(rankingDataGateway.findAll(pageable)).thenReturn(new PageImpl<>(rankings));

    final var result = findRankings.execute(pageable);
    assertNotNull(result);
  }
}