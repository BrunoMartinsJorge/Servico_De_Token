package com.estudosJava.exercicios.controllers;

import com.estudosJava.exercicios.model.dto.AlterarSenhaDTO;
import com.estudosJava.exercicios.model.dto.PerfilDTO;
import com.estudosJava.exercicios.utilities.services.ContaService;
import com.estudosJava.exercicios.utilities.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    private TokenService token = new TokenService();

    @PostMapping("/cadastrar-conta")
    public ResponseEntity<?> adicionarUsuario(@RequestParam String email, @RequestParam String senha, @RequestParam String nome) {
        return contaService.criarNovaConta(nome, email, senha);
    }

    @GetMapping("/logar-conta")
    public ResponseEntity<?> logarConta(@RequestParam String email, @RequestParam String senha) {
        return contaService.logarNaConta(email, senha);
    }

    @GetMapping
    public PerfilDTO buscarDados(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String email = token.extrairDadosToken(authorizationHeader);
        return contaService.logarViaToken(email);
    }

    @PutMapping(value = "/alterar-dados")
    public ResponseEntity<?> alterarDadosPerfil(@RequestBody AlterarSenhaDTO perfil) {
        return contaService.alterarDadosDaConta(perfil);
    }

    @PostMapping("/cadastrar-conta/funcionario-teste")
    public ResponseEntity<?> adicionarFuncionario(@RequestParam String email, @RequestParam String senha, @RequestParam String nome, @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String emailCadastrante = token.extrairDadosToken(authorizationHeader);
        return contaService.criarNovoFuncionario(nome, email, senha, emailCadastrante);
    }

}
