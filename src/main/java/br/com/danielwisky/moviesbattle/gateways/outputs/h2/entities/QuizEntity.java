package br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities;

import static java.util.Optional.ofNullable;
import static javax.persistence.GenerationType.IDENTITY;

import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.enums.QuizStatus;
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
@Entity(name = "quizzes")
public class QuizEntity implements Serializable {

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
  @JoinColumn(name = "movie_one_id", nullable = false)
  private MovieEntity movieOne;
  @ManyToOne
  @JoinColumn(name = "movie_two_id", nullable = false)
  private MovieEntity movieTwo;
  private String status;
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  public QuizEntity(final Quiz quiz) {
    this.id = quiz.getId();
    this.game = ofNullable(quiz.getGame())
        .map(GameEntity::new)
        .orElse(null);
    this.movieOne = ofNullable(quiz.getMovieOne())
        .map(MovieEntity::new)
        .orElse(null);
    this.movieTwo = ofNullable(quiz.getMovieTwo())
        .map(MovieEntity::new)
        .orElse(null);
    this.status = ofNullable(quiz.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.lastModifiedDate = quiz.getLastModifiedDate();
  }

  public Quiz toDomain() {
    return Quiz.builder()
        .id(this.id)
        .game(ofNullable(this.game)
            .map(GameEntity::toDomain)
            .orElse(null))
        .movieOne(ofNullable(this.movieOne)
            .map(MovieEntity::toDomain)
            .orElse(null))
        .movieTwo(ofNullable(this.movieTwo)
            .map(MovieEntity::toDomain)
            .orElse(null))
        .status(ofNullable(this.status)
            .map(QuizStatus::valueOf)
            .orElse(null))
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}