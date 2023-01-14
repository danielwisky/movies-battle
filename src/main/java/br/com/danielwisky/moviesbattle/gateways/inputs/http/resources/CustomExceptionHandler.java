package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.danielwisky.moviesbattle.domains.exceptions.BusinessValidationException;
import br.com.danielwisky.moviesbattle.domains.exceptions.ResourceNotFoundException;
import br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.response.ErrorResponse;
import java.util.List;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<ErrorResponse> handlerResourceNotFoundException(
      final ResourceNotFoundException ex) {
    log.debug(ex.getMessage(), ex);
    return new ResponseEntity<>(createResponse(ex), buildHttpHeader(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public HttpEntity<ErrorResponse> handlerMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {
    log.debug(ex.getMessage(), ex);
    final var bindingResult = ex.getBindingResult();
    final var fieldErrors = bindingResult.getFieldErrors();
    final var message = this.processFieldErrors(fieldErrors);
    return new ResponseEntity<>(message, buildHttpHeader(), BAD_REQUEST);
  }

  @ExceptionHandler({
      IllegalArgumentException.class,
      ConstraintViolationException.class,
      BusinessValidationException.class
  })
  public HttpEntity<ErrorResponse> handle400Exceptions(final RuntimeException ex) {
    log.debug(ex.getMessage(), ex);
    return new ResponseEntity<>(createResponse(ex), buildHttpHeader(), BAD_REQUEST);
  }

  @ExceptionHandler(Throwable.class)
  public HttpEntity<ErrorResponse> handleThrowable(final Throwable ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(createResponse(ex), buildHttpHeader(), INTERNAL_SERVER_ERROR);
  }

  private HttpHeaders buildHttpHeader() {
    final var responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    return responseHeaders;
  }

  private ErrorResponse createResponse(final Throwable ex) {
    return createResponse(ex.getMessage());
  }

  private ErrorResponse createResponse(final String message) {
    return isNoneBlank(message) ? new ErrorResponse(List.of(message)) : null;
  }

  private ErrorResponse processFieldErrors(final List<FieldError> fieldErrors) {
    final var errors = fieldErrors
        .stream()
        .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
        .toList();
    return new ErrorResponse(errors);
  }
}