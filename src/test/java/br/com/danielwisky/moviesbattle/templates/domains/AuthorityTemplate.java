package br.com.danielwisky.moviesbattle.templates.domains;

import br.com.danielwisky.moviesbattle.domains.Authority;

public class AuthorityTemplate {

  public static Authority validUser() {
    return Authority.builder()
        .id(1)
        .authority("ROLE_USER")
        .build();
  }

  public static Authority validAdmin() {
    return Authority.builder()
        .id(2)
        .authority("ROLE_ADMIN")
        .build();
  }
}