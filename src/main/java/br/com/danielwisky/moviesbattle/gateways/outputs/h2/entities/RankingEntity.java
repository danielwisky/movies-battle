package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;


import static java.util.Optional.ofNullable;
import static javax.persistence.GenerationType.IDENTITY;

import br.com.danielwisky.moviesbattle.domains.Ranking;
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
@Entity(name = "rankings")
public class RankingEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(nullable = false)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private GameEntity game;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
  @Column
  private Double score;
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  public RankingEntity(final Ranking ranking) {
    this.id = ranking.getId();
    this.game = ofNullable(ranking.getGame())
        .map(GameEntity::new)
        .orElse(null);
    this.user = ofNullable(ranking.getUser())
        .map(UserEntity::new)
        .orElse(null);
    this.score = ranking.getScore();
    this.lastModifiedDate = ranking.getLastModifiedDate();
  }

  public Ranking toDomain() {
    return Ranking.builder()
        .id(this.id)
        .game(ofNullable(this.game)
            .map(GameEntity::toDomain)
            .orElse(null))
        .user(ofNullable(this.user)
            .map(UserEntity::toDomain)
            .orElse(null))
        .score(this.score)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}