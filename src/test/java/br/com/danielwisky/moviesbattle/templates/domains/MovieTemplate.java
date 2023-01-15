package br.com.danielwisky.moviesbattle.templates.domains;

import static java.time.LocalDateTime.now;

import br.com.danielwisky.moviesbattle.domains.Movie;

public class MovieTemplate {

  public static Movie validMovieOne() {
    return Movie.builder()
        .id(1L)
        .title("Movie One")
        .year(2023)
        .rate(8.0f)
        .lastModifiedDate(now())
        .build();
  }

  public static Movie validMovieTwo() {
    return Movie.builder()
        .id(2L)
        .title("Movie Two")
        .year(2000)
        .rate(6.0f)
        .lastModifiedDate(now())
        .build();
  }
}
