package com.estudosJava.exercicios.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "O nome de usuário não pode ser vazio.")
    private String nomeUsuario;

    @Email(message = "O e-mail deve ser válido.")
    @NotBlank(message = "O e-mail não pode ser vazio.")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;
}
