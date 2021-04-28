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


    private final FasadeService fasadeService;

    public FasadeController(FasadeService fasadeService) throws Exception
    {
        this.fasadeService = fasadeService;
    }



    @GetMapping("/facade_service")
    public Mono<String> messages() {
        return fasadeService.messages();
    }


    @PostMapping("/facade_service")
    public Mono<Void> addMessage(@RequestBody PayloadText text) throws Exception  {
       return fasadeService.addMessage(text);
    }

}

