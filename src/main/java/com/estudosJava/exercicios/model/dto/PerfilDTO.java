package com.estudosJava.exercicios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerfilDTO {

    @JsonIgnore
    private int id;
    private String nome;
    private String email;
    private String categoria;
    @JsonIgnore
    private String senha;

    public static boolean emailValido(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public PerfilDTO(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
