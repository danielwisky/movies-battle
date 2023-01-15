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
public class Movie implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String title;
  private Integer year;
  private Float rate;
  private LocalDateTime lastModifiedDate;

  public boolean isBetterRatedThan(final Movie anotherMovie) {
    return this.rate >= anotherMovie.getRate();
  }
}