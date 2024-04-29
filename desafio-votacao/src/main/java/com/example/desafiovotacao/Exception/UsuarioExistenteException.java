package com.example.desafiovotacao.Exception;

public class UsuarioExistenteException extends RuntimeException{
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
