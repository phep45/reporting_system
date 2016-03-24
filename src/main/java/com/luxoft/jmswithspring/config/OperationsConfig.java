package com.luxoft.jmswithspring.config;

import com.luxoft.jmswithspring.service.LineValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.luxoft.jmswithspring.*")
public class OperationsConfig {

    private static final int VALID_LINE_LENGTH = 97;

//    @Bean
//    public LineValidator lineValidator() {
//        return new LineValidator(VALID_LINE_LENGTH);
//    }
}
