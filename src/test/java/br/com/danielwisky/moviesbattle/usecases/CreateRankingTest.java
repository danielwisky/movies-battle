package br.com.danielwisky.moviesbattle.usecases;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.Ranking;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.RankingDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.QuizTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("CreateRanking Test Case")
class CreateRankingTest extends UnitTest {

  @InjectMocks
  private CreateRanking createRanking;

  @Mock
  private QuizDataGateway quizDataGateway;

  @Mock
  private RankingDataGateway rankingDataGateway;

  @Captor
  private ArgumentCaptor<Ranking> rankingArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStartPending();
    final var user = UserTemplate.validUser();
    final var quizzes = List.of(
        QuizTemplate.validCorrect(), QuizTemplate.validCorrect(), QuizTemplate.validIncorrect());

    when(quizDataGateway.findAllByGameAndStatusIn(any(), any())).thenReturn(quizzes);

    createRanking.execute(game, user);

    verify(rankingDataGateway).save(rankingArgumentCaptor.capture());

    final var rankingCaptured = rankingArgumentCaptor.getValue();

    assertNotNull(rankingCaptured);
    assertEquals(199.98, rankingCaptured.getScore());
  }
}