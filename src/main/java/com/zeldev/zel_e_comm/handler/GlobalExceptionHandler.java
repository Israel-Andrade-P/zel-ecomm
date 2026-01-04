package com.zeldev.zel_e_comm.handler;

import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(ResourceNotFoundException exp) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(400, NOT_FOUND, exp.getMessage())
        );
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handler(APIException exp) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(400, BAD_REQUEST, exp.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException exp) {
        Map<String, String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors().forEach(err -> {
            var fieldName = ((FieldError) err).getField();
            var message = err.getDefaultMessage();
            errors.putIfAbsent(fieldName, message);
        });
        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorResponse(400, BAD_REQUEST, "Invalid fields", errors)
        );
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePsql(PSQLException exp) {
        if ("23505".equals(exp.getSQLState())) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ErrorResponse(409, CONFLICT, "Product already exists"));
        }
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, INTERNAL_SERVER_ERROR, "Unexpected database error"));
    }
}
