package br.com.danielwisky.moviesbattle.domains.exceptions;

import java.io.Serial;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessValidationException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  public BusinessValidationException(final String message) {
    super(message);
  }
}