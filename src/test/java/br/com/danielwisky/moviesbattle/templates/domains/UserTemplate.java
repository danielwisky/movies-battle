package br.com.danielwisky.moviesbattle.templates.domains;

import br.com.danielwisky.moviesbattle.domains.User;
import java.util.List;

public class UserTemplate {

  public static User validUser() {
    return User.builder()
        .id(1L)
        .username("user")
        .password("user")
        .authorities(List.of(AuthorityTemplate.validUser()))
        .credentialsNonExpired(true)
        .accountNonExpired(true)
        .accountNonLocked(true)
        .enabled(true)
        .build();
  }
}
