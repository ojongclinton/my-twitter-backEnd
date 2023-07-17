package com.twit.configuration;
//package spring.study.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer{
//    private final long MAX_AGE_SECS = 3600;
//
//
//    private String[] allowedOrigins = new String[]{"http://localhost:3000"};
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	log.info("this is it!!");
//        registry.addMapping("/**")
//        .allowedOrigins(allowedOrigins)
//        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS","REDIRECT")
//        .allowedHeaders("*")
//        .allowCredentials(true)
//        .maxAge(MAX_AGE_SECS);
//    }
//}
