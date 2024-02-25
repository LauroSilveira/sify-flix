package com.lauro.sifyflixapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SifyFlixApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SifyFlixApiApplication.class, args);
    }

}
