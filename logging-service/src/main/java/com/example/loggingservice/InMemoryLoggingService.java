package com.example.loggingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class InMemoryLoggingService implements LoggingService {

       private Map<UUID, String> messages = new ConcurrentHashMap<>();

       @Override
       public void addToLog(Message msg) {messages.put(msg.id,msg.txt);}

        @Override
        public Map<UUID, String> log() {return messages;}


}
