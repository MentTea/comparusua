package test.comparusua.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.comparusua.api.model.ErrorResponse;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleInternalServerError(Exception e, HttpServletRequest request) {
        return handle(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BindException.class})
    public final ResponseEntity<ErrorResponse> handleBindException(BindException e, HttpServletRequest request) {
        return handle(e, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handle(Exception e, HttpServletRequest request, HttpStatus responseStatus) {
        log.error(String.format("%s %s failed: '%s'", request.getMethod(), request.getServletPath(), e.getMessage()), e);
        return new ResponseEntity<>(new ErrorResponse(
                Instant.now().toString(),
                responseStatus.value(),
                responseStatus.getReasonPhrase(),
                e.getMessage(),
                request.getServletPath()), responseStatus);
    }
}
