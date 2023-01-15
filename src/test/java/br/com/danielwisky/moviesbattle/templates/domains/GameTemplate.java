package br.com.danielwisky.moviesbattle.templates.domains;

import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.FINISHED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.STARTED;
import static br.com.danielwisky.moviesbattle.domains.enums.GameStatus.START_PENDING;
import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Game;

public class GameTemplate {

  public static Game validStartPending() {
    return Game.builder()
        .id(1L)
        .user(UserTemplate.validUser())
        .status(START_PENDING)
        .lastModifiedDate(now())
        .build();
  }

  public static Game validStarted() {
    return Game.builder()
        .id(3L)
        .user(UserTemplate.validUser())
        .status(STARTED)
        .lastModifiedDate(now())
        .build();
  }

  public static Game validFinished() {
    return Game.builder()
        .id(2L)
        .user(UserTemplate.validUser())
        .status(FINISHED)
        .lastModifiedDate(now())
        .build();
  }
}
