package com.iginicaospring.programtransito.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // indica que é uma classe de configuração
public class ModelMapperConfig {

    @Bean // indica a criação de uma bean a ser gerenciado pelo Spring
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
