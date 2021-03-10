package com.example.loggingservice;

import java.util.UUID;

public class Message {
    public final UUID id;
    public final String txt;


    public Message(UUID uuid, String text) {
        id = uuid;
        txt = text;
    }

    public String ToString(UUID uuid, String text)
    {
      return  ("UUID: " + uuid + " MESSAGE: " + text).toString();

    }
}
