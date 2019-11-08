package com.fsh.demo.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("MyConfig. addCorsMappings()");
        registry.addMapping("**")
                .allowedOrigins("*").allowedMethods("POST, GET, HEAD, OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers")
                .exposedHeaders("Access-Control-Allow-Origin,Access-Control-Allow-Credentials")
                .maxAge(10);
    }
}
