package com.estudosJava.exercicios.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperModel {

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
