package br.com.danielwisky.moviesbattle.domains;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String authority;
}