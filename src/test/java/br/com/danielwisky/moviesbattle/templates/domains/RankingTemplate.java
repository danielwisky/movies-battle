package br.com.danielwisky.moviesbattle.templates.domains;

import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Ranking;

public class RankingTemplate {

  public static Ranking valid() {
    return Ranking.builder()
        .id(1L)
        .user(UserTemplate.validUser())
        .game(GameTemplate.validFinished())
        .score(200d)
        .lastModifiedDate(now())
        .build();
  }
}
