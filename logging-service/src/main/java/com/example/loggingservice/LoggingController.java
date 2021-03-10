package com.example.loggingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class LoggingController {
    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private Map<UUID,String> messages = new ConcurrentHashMap<>();


    @PostMapping("/log")
    public ResponseEntity<Void> log(@RequestBody Message msg)
    {
        logger.info(msg.ToString(msg.id, msg.txt));
        messages.put(msg.id, msg.txt);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/log")
    public String listLog() {return messages.values().toString();}



}
