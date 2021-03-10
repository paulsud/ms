package com.example.fasadeservice;


import org.apache.logging.log4j.message.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class FasadeController {

    WebClient loggingWebClient = WebClient.create("http://localhost:8081");
    WebClient messagesWebClient = WebClient.create("http://localhost:8082");


    @GetMapping("/facade_service")
    public Mono<String> clienWebClient() {

        Mono<String> cashedValues = loggingWebClient.get()
                .uri("/log")
                .retrieve()
                .bodyToMono(String.class);


        Mono<String> messageMono = messagesWebClient.get()
                .uri("/message")
                .retrieve()
                .bodyToMono(String.class);

        return cashedValues.zipWith(messageMono,
                (cached, message) -> cached + ": " + message)
                            .onErrorReturn("Error");
    }


    @PostMapping("/facade_service")
    public Mono<Void> facadeWebClient(@RequestBody PayloadText text) {
        var msg = new com.example.fasadeservice.Message(UUID.randomUUID(), text.txt);

        return loggingWebClient.post()
                .uri("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(msg), Message.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

}

