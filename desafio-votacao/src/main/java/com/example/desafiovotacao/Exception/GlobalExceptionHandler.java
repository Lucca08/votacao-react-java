package com.example.desafiovotacao.Exception;

import com.example.desafiovotacao.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PautaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handlePautaNotFoundException(PautaNaoEncontradaException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Pauta não encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        ErrorResponse errorResponse = new ErrorResponse("Ocorreu um erro interno no servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(PautaNaoCriadaException.class)
    public ResponseEntity<ErrorResponse> handlePautaNaoCriadaException(PautaNaoCriadaException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Pauta não criada");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
