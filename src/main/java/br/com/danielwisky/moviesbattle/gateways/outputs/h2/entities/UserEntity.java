package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity implements UserDetails, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Long id;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @ManyToMany(fetch = EAGER)
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Collection<AuthorityEntity> authorities;
  @Column(name = "account_non_expired")
  private boolean accountNonExpired;
  @Column(name = "account_non_locked")
  private boolean accountNonLocked;
  @Column(name = "credentials_non_expired")
  private boolean credentialsNonExpired;
  @Column
  private boolean enabled;
}