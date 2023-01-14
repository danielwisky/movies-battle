package br.com.danielwisky.moviesbattle.gateways.inputs.http;

import static org.springframework.http.HttpStatus.OK;

import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.QuizResponse;
import br.com.danielwisky.moviesbattle.usecases.AnswerQuiz;
import br.com.danielwisky.moviesbattle.usecases.FindActualQuiz;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games/{gameId}/quizzes")
public class QuizController {

  private final FindActualQuiz findActualQuiz;
  private final AnswerQuiz answerQuiz;

  @GetMapping(value = "/actual")
  @Operation(summary = "Get Current Quiz")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public QuizResponse get(@PathVariable final Long gameId, final Authentication authentication) {
    final var user = (User) authentication.getPrincipal();
    return new QuizResponse(findActualQuiz.execute(gameId, user));
  }

  @PutMapping(value = "/{quizId}/answers")
  @Operation(summary = "End Game")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "409", description = "Conflict"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public QuizResponse end(@PathVariable final Long gameId, @PathVariable final Long quizId,
      final Authentication authentication) {
    return null;
  }
}
