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

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FasadeService {

    Logger logger = LoggerFactory.getLogger(FasadeService.class);


    private List<WebClient> loggingWebClients;
    private  WebClient messagesWebClient;
    private  WebClient  loggingWebClient;
    public FasadeService() {
        loggingWebClients = List.of(
       WebClient.create("http://localhost:8082"),
       WebClient.create("http://localhost:8083"),
                WebClient.create("http://localhost:8084")
        );
        messagesWebClient = WebClient.create("http://localhost:8081");
    }

    public Mono<Void> addMessage(PayloadText text) {
        var msg = new com.example.fasadeservice.Message(UUID.randomUUID(), text.txt);
          //      (UUID.randomUUID(), text.txt);


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


        var messageMono = messagesWebClient.get()
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




}

