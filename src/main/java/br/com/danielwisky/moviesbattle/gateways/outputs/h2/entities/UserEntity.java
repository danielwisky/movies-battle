package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.danielwisky.moviesbattle.domains.User;
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

@Data
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity implements Serializable {

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

  public UserEntity(final User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.authorities = emptyIfNull(user.getAuthorities())
        .stream()
        .map(AuthorityEntity::new)
        .toList();
    this.accountNonExpired = user.isAccountNonExpired();
    this.accountNonLocked = user.isAccountNonLocked();
    this.credentialsNonExpired = user.isCredentialsNonExpired();
    this.enabled = user.isEnabled();
  }

  public User toDomain() {
    return User.builder()
        .id(this.id)
        .username(this.username)
        .password(this.password)
        .authorities(emptyIfNull(this.authorities)
            .stream()
            .map(AuthorityEntity::toDomain)
            .toList())
        .accountNonExpired(this.accountNonExpired)
        .accountNonLocked(this.accountNonLocked)
        .credentialsNonExpired(this.credentialsNonExpired)
        .enabled(this.enabled)
        .build();
  }
}