package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources;

import static java.util.Optional.ofNullable;

import br.com.danielwisky.moviesbattle.domains.Quiz;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private MovieResponse movieOne;
  private MovieResponse movieTwo;
  private String status;
  private LocalDateTime lastModifiedDate;

  public QuizResponse(final Quiz quiz) {
    this.id = quiz.getId();
    this.movieOne = ofNullable(quiz.getMovieOne())
        .map(MovieResponse::new)
        .orElse(null);
    this.movieTwo = ofNullable(quiz.getMovieTwo())
        .map(MovieResponse::new)
        .orElse(null);
    this.status = ofNullable(quiz.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.lastModifiedDate = quiz.getLastModifiedDate();
  }
}
