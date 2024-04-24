package com.example.desafiovotacao.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 65, message = "Nome deve ter no máximo 65 caracteres")
    private String nome;

}
