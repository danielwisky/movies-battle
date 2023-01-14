package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static javax.persistence.GenerationType.IDENTITY;

import br.com.danielwisky.moviesbattle.domains.Authority;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "authorities")
public class AuthorityEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Integer id;
  @Column(nullable = false, unique = true)
  private String authority;

  public AuthorityEntity(final Authority authority) {
    this.id = authority.getId();
    this.authority = authority.getAuthority();
  }

  public Authority toDomain() {
    return Authority.builder()
        .id(this.id)
        .authority(this.authority)
        .build();
  }
}
