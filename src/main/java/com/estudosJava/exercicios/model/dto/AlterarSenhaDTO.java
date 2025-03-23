package com.estudosJava.exercicios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlterarSenhaDTO {

    private String email;
    private String senha;
    private String senhaNova;
}
