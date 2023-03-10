package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static javax.persistence.GenerationType.IDENTITY;

import br.com.danielwisky.moviesbattle.domains.Movie;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "movies")
public class MovieEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Long id;
  @Column(nullable = false, length = 100)
  private String title;
  @Column(name = "release_year")
  private Integer year;
  @Column
  private Float rate;
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  public MovieEntity(final Movie movie) {
    this.id = movie.getId();
    this.title = movie.getTitle();
    this.year = movie.getYear();
    this.rate = movie.getRate();
    this.lastModifiedDate = movie.getLastModifiedDate();
  }

  public Movie toDomain() {
    return Movie.builder()
        .id(this.id)
        .title(this.title)
        .year(this.year)
        .rate(this.rate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}