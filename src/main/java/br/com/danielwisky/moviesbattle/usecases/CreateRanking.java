package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.CORRECT;
import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.INCORRECT;
import static java.math.RoundingMode.FLOOR;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.Ranking;
import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.RankingDataGateway;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateRanking {

  private final QuizDataGateway quizDataGateway;
  private final RankingDataGateway rankingDataGateway;

  /**
   * Creates a new ranking for the provided game and user.
   *
   * @param game the game for which the ranking is being created
   * @param user the user for which the ranking is being created
   */
  public void execute(final Game game, final User user) {
    final var quizzes = quizDataGateway.findAllByGameAndStatusIn(game, List.of(CORRECT, INCORRECT));
    final var total = quizzes.stream().count();
    final var totalCorrect = quizzes.stream().filter(Quiz::isCorrect).count();
    final double percentage = totalCorrect > 0 ? calculatePercentage(total, totalCorrect) : 0.0;
    final double score = percentage * total;

    final var newRanking = Ranking.builder()
        .game(game)
        .user(user)
        .score(score)
        .lastModifiedDate(now())
        .build();
    rankingDataGateway.save(newRanking);
  }

  /**
   * Calculates the percentage of correct answers.
   *
   * @param total        the total number of quizzes
   * @param totalCorrect the total number of correct answers
   * @return the percentage of correct answers
   */
  private static double calculatePercentage(final long total, final long totalCorrect) {
    return BigDecimal.valueOf(totalCorrect * 100.0f / total).setScale(2, FLOOR).doubleValue();
  }
}