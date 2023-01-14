package br.com.danielwisky.moviesbattle.domains;

import br.com.danielwisky.moviesbattle.domains.enums.GameStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private User user;
  private GameStatus status;
  private LocalDateTime lastModifiedDate;
}
