package br.com.danielwisky.moviesbattle.domains.exceptions;

import java.io.Serial;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}