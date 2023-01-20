package org.example.demo.handler.service;


import org.example.demo.messages.BaseMessage;
import org.example.demo.messages.MessageA;
import org.example.demo.messages.MessageB;

public interface MessageHandler {

    void handle(MessageA message);

    void handle(MessageB message);

    void handle(BaseMessage message);

}
