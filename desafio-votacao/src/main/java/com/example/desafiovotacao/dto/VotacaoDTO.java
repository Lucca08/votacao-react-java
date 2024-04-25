package com.example.desafiovotacao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDTO {

    @NotNull(message = "voto é obrigatório")
    private boolean voto;
}
