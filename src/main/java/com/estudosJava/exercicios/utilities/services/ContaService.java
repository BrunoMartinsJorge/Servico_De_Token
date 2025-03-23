package com.estudosJava.exercicios.utilities.services;

import com.estudosJava.exercicios.model.dto.AlterarSenhaDTO;
import com.estudosJava.exercicios.model.dto.PerfilDTO;
import com.estudosJava.exercicios.model.entities.Perfil;
import com.estudosJava.exercicios.utilities.exceptions.CadastroException;
import com.estudosJava.exercicios.utilities.repositories.ContaRepositories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class ContaService {

    @Autowired
    private ContaRepositories repositories;

    @Autowired
    private TokenService token;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<?> criarNovaConta(String nome, String email, String senha) {
        Perfil perfil = new Perfil();
        if (PerfilDTO.emailValido(email)) {
            perfil.setEmail(email);
        } else {
            throw new RuntimeException("Email inválido inserido!");
        }
        perfil.setSenha(senha);
        perfil.setNomeUsuario(nome);
        if (!repositories.existsByEmail(email)) {
            perfil.setCategoria(TipoUsuario.RULE_CLIENTE.toString());
            repositories.save(perfil);
            return ResponseEntity.ok().body("{\"token\": \"" + token.criarToken(perfil) + "\"}");
        } else {
            throw CadastroException.EmailJaCdastrado(perfil.getEmail());
        }
    }

    public ResponseEntity<?> criarNovoFuncionario(String nome, String email, String senha, String emailCadastrante) {
        Perfil dados = new Perfil();
            Predicate<String> estaPermitidoCriarNovoFuncionario = login -> repositories.existsByEmail(login) && repositories.findByEmail(login).getCategoria().equals(TipoUsuario.RULE_ADMINISTRADOR.toString());
            Predicate<String> usuarioJaExiste = user -> repositories.existsByEmail(user);
        if (estaPermitidoCriarNovoFuncionario.test(emailCadastrante)) {
            if(usuarioJaExiste.test(email)){
                throw CadastroException.EmailJaCdastrado(email);
            }
            dados.setNomeUsuario(nome);
            dados.setEmail(email);
            dados.setSenha(senha);
            dados.setCategoria(TipoUsuario.RULE_FUNCIONARIO.toString());
            repositories.save(dados);
        }else{
            throw new RuntimeException("Você não tem permissão para gerar uma nova conta para funcionário!");
        }
        return ResponseEntity.ok().body("{\"token\":  \"" + token.criarToken(dados) + "\"}");
    }

    public ResponseEntity<?> logarNaConta(String email, String senha) {
        Perfil dadosDaConta = repositories.findByEmail(email);
        if (dadosDaConta != null) {
            if (senha.equals(dadosDaConta.getSenha())) {
                return ResponseEntity.ok().body("{\"token\": \"" + token.criarToken(dadosDaConta) + "\"}");
            } else {
                throw CadastroException.SenhaInvalida();
            }
        } else {
            throw CadastroException.ContaNaoCadastrada(email);
        }
    }

    public ResponseEntity<?> alterarDadosDaConta(AlterarSenhaDTO perfil) {
        Perfil dadosDaConta = repositories.findByEmail(perfil.getEmail());
        if (dadosDaConta != null) {
            if (dadosDaConta.getSenha().equals(perfil.getSenha())) {
                dadosDaConta.setSenha(perfil.getSenhaNova());
                repositories.save(dadosDaConta);
            } else {
                throw CadastroException.SenhaInvalida();
            }
        } else {
            throw CadastroException.ContaNaoCadastrada(perfil.getEmail());
        }
        return ResponseEntity.ok().body(dadosDaConta.getSenha());
    }

    public PerfilDTO logarViaToken(String email) {
        Perfil dadosConta = repositories.findByEmail(email);
        return mapper.map(dadosConta, PerfilDTO.class);
    }
}
