package com.estudosJava.exercicios.utilities.services;

import com.estudosJava.exercicios.model.entities.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {
    final SecretKey CHAVE = Keys.hmacShaKeyFor(
            "7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6"
                    .getBytes(StandardCharsets.UTF_8));

    public String criarToken(Perfil dadosConta) {
        if (dadosConta == null || dadosConta.getEmail() == null) {
            throw new IllegalArgumentException("Perfil inválido para gerar token.");
        }

        return Jwts.builder()
                .claim("id", dadosConta.getId())
                .claim("nomeUsuario", dadosConta.getNomeUsuario())
                .claim("email", dadosConta.getEmail())
                .claim("permições", dadosConta.getCategoria())
                .setIssuer(String.valueOf(Jwts.header().isEmpty()))
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusHours(2L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()))
                .signWith(CHAVE, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extrairDadosToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválida ou não autori");
        }
        String tokenStr = authorizationHeader.substring(7); // Remove o prefixo "Bearer "
        return extrairEmailDoToken(tokenStr);
    }

    private String extrairEmailDoToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("email", String.class);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Token inválido ou expirado.");
        }
    }
}