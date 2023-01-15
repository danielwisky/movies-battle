package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources;

import br.com.danielwisky.moviesbattle.domains.enums.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AnswerRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(allowableValues = {"MOVIE_ONE", "MOVIE_TWO"})
  @Pattern(regexp = "MOVIE_ONE|MOVIE_TWO")
  private String answer;

  public Answer toDomain() {
    return Answer.valueOf(answer);
  }
}