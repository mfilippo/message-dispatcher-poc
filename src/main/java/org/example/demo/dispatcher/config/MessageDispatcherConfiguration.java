package org.example.demo.dispatcher.config;

import org.example.demo.dispatcher.service.MessageDispatcher;
import org.example.demo.handler.service.MessageHandler;
import org.example.demo.messages.BaseMessage;
import org.example.demo.messages.MessageA;
import org.example.demo.messages.MessageB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Profile("!test")
@Configuration
public class MessageDispatcherConfiguration {

    @Autowired
    MessageDispatcher<BaseMessage> messageDispatcher;

    @Autowired
    MessageHandler messageHandler;

    @EventListener(ContextRefreshedEvent.class)
    public void afterBeansCreation() {
        messageDispatcher.register(MessageA.class, m -> messageHandler.handle(m));
        messageDispatcher.register(MessageB.class, m -> messageHandler.handle(m));
        messageDispatcher.registerDefault(m -> messageHandler.handle(m));
    }
}
