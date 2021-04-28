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

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService)
    {
        this.loggingService = loggingService;
    }


    @GetMapping("/log")
    public String listLog()
    {
        Map<UUID, String> messages = loggingService.log();
        return messages.values().toString();

    }


    @PostMapping("/log")
    public ResponseEntity<Void> log(@RequestBody Message msg)
    {
        logger.info(msg.ToString(msg.id, msg.txt));
      //  messages.put(msg.id, msg.txt);
        loggingService.addToLog(msg);
        return ResponseEntity.ok().build();
    }


}
