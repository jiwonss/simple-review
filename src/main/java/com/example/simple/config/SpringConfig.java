package com.example.simple.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class SpringConfig {

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return hiddenHttpMethodFilter;
    }
}
