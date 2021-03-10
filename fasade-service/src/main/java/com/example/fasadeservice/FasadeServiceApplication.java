package com.example.fasadeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FasadeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FasadeServiceApplication.class, args);
    }

}
