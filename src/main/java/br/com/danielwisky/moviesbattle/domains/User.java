package br.com.danielwisky.moviesbattle.domains;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String password;
  private Collection<Authority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
}
