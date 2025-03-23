package com.estudosJava.exercicios.utilities.repositories;

import com.estudosJava.exercicios.model.dto.PerfilDTO;
import com.estudosJava.exercicios.model.entities.Perfil;
import org.springframework.data.repository.CrudRepository;

public interface ContaRepositories extends CrudRepository<Perfil, Integer> {

    public boolean existsByEmail(String email);

    public Perfil findByEmail(String email);
}
