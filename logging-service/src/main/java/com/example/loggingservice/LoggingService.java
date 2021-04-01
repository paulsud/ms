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


public interface LoggingService {

    void addToLog(Message msg);
    Map<UUID, String> log();


}
