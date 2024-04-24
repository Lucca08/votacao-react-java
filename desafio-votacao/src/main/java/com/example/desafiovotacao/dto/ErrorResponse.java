package com.example.desafiovotacao.dto;

public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

   public void setMessage(String message) {
         this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
