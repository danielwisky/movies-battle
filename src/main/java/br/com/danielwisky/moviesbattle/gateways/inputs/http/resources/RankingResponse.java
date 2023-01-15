package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.moviesbattle.domains.Ranking;
import br.com.danielwisky.moviesbattle.domains.User;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private GameResponse game;
  private String user;
  private Double score;
  private LocalDateTime lastModifiedDate;

  public RankingResponse(final Ranking ranking) {
    this.id = ranking.getId();
    this.game = ofNullable(ranking.getGame())
        .map(GameResponse::new)
        .orElse(null);
    this.user = ofNullable(ranking.getUser())
        .map(User::getUsername)
        .orElse(null);
    this.score = ranking.getScore();
    this.lastModifiedDate = game.getLastModifiedDate();
  }
}