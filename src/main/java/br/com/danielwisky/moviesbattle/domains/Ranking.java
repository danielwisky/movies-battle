package br.com.danielwisky.moviesbattle.domains;

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
public class Ranking implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private Game game;
  private User user;
  private Double score;
  private LocalDateTime lastModifiedDate;
}