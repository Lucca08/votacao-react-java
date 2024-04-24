package com.example.desafiovotacao.dto;

import jakarta.validation.constraints.NotBlank;

public class Votacao {

    @NotBlank(message = "voto é obrigatório")
    private boolean voto;
}
