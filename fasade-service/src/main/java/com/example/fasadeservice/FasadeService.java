package com.example.fasadeservice;


import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FasadeService {
    private static final String TASK_QUEUE_NAME = "NewQ";

    Logger logger = LoggerFactory.getLogger(FasadeService.class);

   private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private List<WebClient> loggingWebClients;
    private List<WebClient> messageWebClients;
    private  WebClient messageWebClient;
    private  WebClient  loggingWebClient;
    public FasadeService() throws Exception{
        loggingWebClients = List.of(
         WebClient.create("http://localhost:8081"),
        WebClient.create("http://localhost:8082"),
        WebClient.create("http://localhost:8083")
        );
        messageWebClients = List.of(
                WebClient.create("http://localhost:8084"),
                WebClient.create("http://localhost:8085")
        );
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

    }

    public Mono<Void> addMessage(PayloadText text) throws Exception  {
        var msg = new com.example.fasadeservice.Message(UUID.randomUUID(), text.txt);
        String message = text.txt;

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

        var loggingWebClient = getRandomLoggingClient();
        logger.info(loggingWebClient.toString());

        return loggingWebClient.post()
                .uri("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(msg), Message.class)
                .retrieve()
                .bodyToMono(Void.class);
    }



    public Mono<String> messages() {

        var loggingWebClient = getRandomLoggingClient();

        var logValuesMono = loggingWebClient.get()
                .uri("/log")
                .retrieve()
                .bodyToMono(String.class);


        var messageWebClient = getRandomMessageClient();

        var messageMono = messageWebClient.get()
                .uri("/message")
                .retrieve()
                .bodyToMono(String.class);

        return logValuesMono.zipWith(messageMono,
                (cached, message) -> cached + ": " + message)
                .onErrorReturn("Error");
    }

    private WebClient getRandomLoggingClient(){
        Random random = new Random();
        var loggingWebclient = loggingWebClients.get(random.nextInt(loggingWebClients.size()));
        return loggingWebclient;
    }


    private WebClient getRandomMessageClient(){
        Random random = new Random();
        var messageWebclient = messageWebClients.get(random.nextInt(messageWebClients.size()));
        return messageWebclient;
    }



}

