package com.bolashak.onlinestorebackend.config;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper;
    }
}

