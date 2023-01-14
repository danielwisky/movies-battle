package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private List<String> errors = new ArrayList<>();

  public void addError(final String error) {
    errors.add(error);
  }
}