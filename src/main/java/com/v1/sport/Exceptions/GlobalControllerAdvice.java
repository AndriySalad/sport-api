package com.v1.sport.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, List<ValidationErrorEntry>> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            if (!errors.containsKey(field)) {
                errors.put(field, new ArrayList<>());
            }
            errors.get(field).add(new ValidationErrorEntry(error.getCode(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            String objectName = error.getObjectName();
            if (!errors.containsKey(objectName)) {
                errors.put(objectName, new ArrayList<>());
            }
            errors.get(objectName).add(new ValidationErrorEntry(error.getCode(), error.getDefaultMessage()));
        }
        ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, validationError, headers, validationError.getHttpStatus(), request);
    }

    @ExceptionHandler(GenericValidationException.class)
    public ResponseEntity<Object> handleGenericValidationException(GenericValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage(exception.getMessage()).build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handle401(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .errorMessage("Unauthorized").build()
        );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handle423(LockedException ex) {
        return ResponseEntity.status(HttpStatus.LOCKED).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.LOCKED)
                        .errorMessage("Sorry, your account is locked").build()
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        if (ex.getMessage().toLowerCase().indexOf("access is denied") > -1) {
            return new ResponseEntity<Object>("Unauthorized Access", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(exception.getMessage()).build()
        );
    }



    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<Object> handle403(PermissionDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .errorMessage("Forbidden").build()
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGenericException(Throwable exception) {
        String errId = RandomStringUtils.randomAlphanumeric(8);
        String errorMessage = "Internal error (error id=" + errId + ")";
        log.error(errorMessage, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GenericError.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessage(errorMessage).build()
        );
    }
}
