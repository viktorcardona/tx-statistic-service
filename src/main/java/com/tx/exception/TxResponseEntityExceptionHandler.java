package com.tx.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@ControllerAdvice
@RestController
public class TxResponseEntityExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleTxInvalidFormatException(HttpMessageNotReadableException ex, WebRequest request) {
        HttpStatus httpStatus = Optional.ofNullable(ex.getCause())
                .filter(this::isJsonInvalid)
                .map(x -> HttpStatus.BAD_REQUEST)
                .orElse(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity(httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler(TimeException.class)
    public void invalidTimeExceptionHandler() {
        //System.out.printf("TimeException.....................>>>>>>>>>>");
    }

    private boolean isJsonInvalid(Throwable exc) {
        return (exc instanceof JsonParseException
                || exc.getClass().getName().equals(MismatchedInputException.class.getName()));
    }

}
