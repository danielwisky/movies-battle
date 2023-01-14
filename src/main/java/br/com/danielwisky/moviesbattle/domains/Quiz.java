package br.com.danielwisky.moviesbattle.domains;

import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
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
public class Quiz implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private QuizStatus status;
  private Game game;
  private Movie movieOne;
  private Movie movieTwo;
  private LocalDateTime lastModifiedDate;
}
