package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.response;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.moviesbattle.domains.Game;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String status;
  private LocalDateTime lastModifiedDate;

  public GameResponse(final Game game) {
    this.id = game.getId();
    this.status = ofNullable(game.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.lastModifiedDate = game.getLastModifiedDate();
  }
}
