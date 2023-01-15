package br.com.danielwisky.moviesbattle.gateways.inputs.http;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.ListResponse;
import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.RankingResponse;
import br.com.danielwisky.moviesbattle.usecases.FindRankings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rankings")
public class RankingController {

  private final FindRankings findRankings;

  @GetMapping
  @Operation(summary = "Get All Rankings")
  @ResponseStatus(OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Ok"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<ListResponse<RankingResponse>> getAll(
      @RequestParam(required = false, defaultValue = "0") final int pageNumber,
      @RequestParam(required = false, defaultValue = "20") final int pageSize) {
    final var pageRequest = PageRequest.of(pageNumber, pageSize, DESC, "score");
    final var pageResponse = findRankings.execute(pageRequest).map(RankingResponse::new);
    return ResponseEntity.ok(new ListResponse<>(pageResponse));
  }
}