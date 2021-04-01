package com.example.loggingservice;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Primary
public class HazelcastLoggingService implements LoggingService {

    private HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private Map<UUID, String> messages = hzInstance.getMap("logging_map");

    @Override
    public void addToLog(Message msg) {messages.put(msg.id, msg.txt);}

    @Override
    public Map<UUID,String> log() {return messages;}
}
