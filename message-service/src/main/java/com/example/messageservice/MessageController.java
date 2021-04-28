package com.example.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;



@RestController
public class MessageController {

    private final MessageService messageService;



    public MessageController(MessageService messageService)
    {
        this.messageService = messageService;
    }


    @GetMapping("/message")
    public String GetResult() throws Exception {
        return messageService.getResult();
    }



}
