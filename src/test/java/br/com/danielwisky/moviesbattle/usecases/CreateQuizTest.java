package br.com.danielwisky.moviesbattle.usecases;

import static br.com.danielwisky.moviesbattle.domains.enums.QuizStatus.NOT_ANSWERED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import br.com.danielwisky.moviesbattle.UnitTest;
import br.com.danielwisky.moviesbattle.domains.Quiz;
import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.gateways.outputs.MovieDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.QuizDataGateway;
import br.com.danielwisky.moviesbattle.templates.domains.GameTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.MovieTemplate;
import br.com.danielwisky.moviesbattle.templates.domains.UserTemplate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("CreateQuiz Test Case")
class CreateQuizTest extends UnitTest {

  @InjectMocks
  private CreateQuiz createQuiz;

  @Mock
  private MovieDataGateway movieDataGateway;

  @Mock
  private QuizDataGateway quizDataGateway;

  @Mock
  private EndGame endGame;

  @Captor
  private ArgumentCaptor<Quiz> quizArgumentCaptor;

  @Test
  @DisplayName("should execute")
  void shouldExecute() {
    final var game = GameTemplate.validStarted();
    final var user = UserTemplate.validUser();
    final var movieOne = MovieTemplate.validMovieOne();
    final var movieTwo = MovieTemplate.validMovieTwo();

    when(movieDataGateway.findTwoRandomMovies()).thenReturn(List.of(movieOne, movieTwo));
    when(quizDataGateway.existsByGameAndMovies(game, movieOne, movieTwo)).thenReturn(false);

    createQuiz.execute(game, user);

    verifyNoInteractions(endGame);
    verify(quizDataGateway).save(quizArgumentCaptor.capture());

    final var quizCaptured = quizArgumentCaptor.getValue();

    assertNotNull(quizCaptured);
    assertEquals(NOT_ANSWERED, quizCaptured.getStatus());
    assertEquals(movieOne, quizCaptured.getMovieOne());
    assertEquals(movieTwo, quizCaptured.getMovieTwo());
  }

  @Test
  @DisplayName("should throw exception when not finding available movies")
  void shouldThrowExceptionWhenNotFindingAvailableMovies() {
    final var game = GameTemplate.validStarted();
    final var user = UserTemplate.validUser();
    final var movieOne = MovieTemplate.validMovieOne();
    final var movieTwo = MovieTemplate.validMovieTwo();

    when(movieDataGateway.findTwoRandomMovies()).thenReturn(List.of(movieOne, movieTwo));
    when(quizDataGateway.existsByGameAndMovies(game, movieOne, movieTwo)).thenReturn(true);

    assertThrows(BusinessValidationException.class, () -> createQuiz.execute(game, user));

    verify(endGame).execute(game.getId(), user);
  }
}