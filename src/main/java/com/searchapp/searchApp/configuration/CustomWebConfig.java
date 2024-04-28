package com.searchapp.searchApp.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomWebConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}







