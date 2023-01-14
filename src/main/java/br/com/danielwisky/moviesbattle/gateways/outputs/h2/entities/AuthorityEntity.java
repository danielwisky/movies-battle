package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@Entity(name = "authorities")
public class AuthorityEntity implements GrantedAuthority, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Integer id;
  @Column(nullable = false, unique = true)
  private String authority;
}
