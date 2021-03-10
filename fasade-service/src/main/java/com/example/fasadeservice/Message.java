package com.example.fasadeservice;

import java.util.UUID;

public class Message {

    public final UUID id;
    public final String txt;


    public Message(UUID uuid, String text) {
        id = uuid;
        txt = text;
    }
}
