package br.com.danielwisky.moviesbattle.gateways.inputs.http;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import br.com.danielwisky.moviesbattle.domains.User;
import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.GameResponse;
import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.ListResponse;
import br.com.danielwisky.moviesbattle.usecases.CreateGame;
import br.com.danielwisky.moviesbattle.usecases.EndGame;
import br.com.danielwisky.moviesbattle.usecases.FindGame;
import br.com.danielwisky.moviesbattle.usecases.FindGames;
import br.com.danielwisky.moviesbattle.usecases.StartGame;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {

  private final CreateGame createGame;
  private final StartGame startGame;
  private final EndGame endGame;
  private final FindGame findGame;
  private final FindGames findGames;

  @PostMapping
  @Operation(summary = "Create Game")
  @ResponseStatus(CREATED)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "409", description = "Conflict"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public GameResponse create(final Authentication authentication) {
    final var user = (User) authentication.getPrincipal();
    log.debug("create game with user: {}", user.getUsername());
    return new GameResponse(createGame.execute(user));
  }

  @PutMapping(value = "/{id}/start")
  @Operation(summary = "Start Game")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "409", description = "Conflict"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public GameResponse start(@PathVariable final Long id, final Authentication authentication) {
    final var user = (User) authentication.getPrincipal();
    log.debug("start game with id: {} and user: {}", id, user.getUsername());
    return new GameResponse(startGame.execute(id, user));
  }

  @PutMapping(value = "/{id}/end")
  @Operation(summary = "End Game")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "409", description = "Conflict"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public GameResponse end(@PathVariable final Long id, final Authentication authentication) {
    final var user = (User) authentication.getPrincipal();
    log.debug("end game with id: {} and user: {}", id, user.getUsername());
    return new GameResponse(endGame.execute(id, user));
  }

  @GetMapping(value = "/{id}")
  @Operation(summary = "Get Game by Id")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public GameResponse get(@PathVariable final Long id, final Authentication authentication) {
    final var user = (User) authentication.getPrincipal();
    return new GameResponse(findGame.execute(id, user));
  }

  @GetMapping
  @Operation(summary = "Get All Games")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<ListResponse<GameResponse>> getAll(
      final Authentication authentication,
      @RequestParam(required = false, defaultValue = "0") final int pageNumber,
      @RequestParam(required = false, defaultValue = "20") final int pageSize) {
    final var user = (User) authentication.getPrincipal();
    final var pageRequest = PageRequest.of(pageNumber, pageSize, DESC, "id");
    final var pageResponse = findGames.execute(user, pageRequest).map(GameResponse::new);
    return ResponseEntity.ok(new ListResponse<>(pageResponse));
  }
}