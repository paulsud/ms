package com.example.fasadeservice;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FasadeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FasadeServiceApplication.class, args);
    }

}
