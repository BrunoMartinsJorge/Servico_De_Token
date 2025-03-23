package com.estudosJava.exercicios.utilities.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class CadastroException extends RuntimeException{

    static public RuntimeException EmailJaCdastrado(String email){
         throw new RuntimeException("O email " + email + " já está cadastrado no banco de dados!");
    }

    static public RuntimeException ContaNaoCadastrada(String email){
        throw new RuntimeException("O email " + email + " não está cadastrado no banco de dados!");
    }

    static public RuntimeException SenhaInvalida(){
        throw new RuntimeException("A senha está invalida para login!");
    }
}
