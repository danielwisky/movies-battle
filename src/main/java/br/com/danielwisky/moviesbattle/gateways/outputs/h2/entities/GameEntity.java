package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static java.util.Optional.ofNullable;
import static javax.persistence.GenerationType.IDENTITY;

import br.com.danielwisky.moviesbattle.domains.Game;
import br.com.danielwisky.moviesbattle.domains.enums.GameStatus;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "games")
public class GameEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
  private String status;
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  public GameEntity(final Game game) {
    this.id = game.getId();
    this.user = ofNullable(game.getUser())
        .map(UserEntity::new)
        .orElse(null);
    this.status = ofNullable(game.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.lastModifiedDate = game.getLastModifiedDate();
  }

  public Game toDomain() {
    return Game.builder()
        .id(this.id)
        .user(ofNullable(this.user)
            .map(UserEntity::toDomain)
            .orElse(null))
        .status(ofNullable(this.status)
            .map(GameStatus::valueOf)
            .orElse(null))
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}
