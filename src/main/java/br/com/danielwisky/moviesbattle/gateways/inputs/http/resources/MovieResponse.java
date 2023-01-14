package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources;

import br.com.danielwisky.moviesbattle.domains.Movie;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String title;
  private Integer year;
  private LocalDateTime lastModifiedDate;

  public MovieResponse(final Movie movie) {
    this.id = movie.getId();
    this.title = movie.getTitle();
    this.year = movie.getYear();
    this.lastModifiedDate = movie.getLastModifiedDate();
  }
}