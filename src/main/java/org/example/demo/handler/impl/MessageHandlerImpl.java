package org.example.demo.handler.impl;

import org.example.demo.handler.service.MessageHandler;
import org.example.demo.messages.BaseMessage;
import org.example.demo.messages.MessageA;
import org.example.demo.messages.MessageB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerImpl implements MessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerImpl.class);

    @Override
    public void handle(MessageA message) {
        LOGGER.info("Handling message: MessageA");
    }

    @Override
    public void handle(MessageB message) {
        LOGGER.info("Handling message: MessageB");
    }

    @Override
    public void handle(BaseMessage message) {
        LOGGER.info("Handling message: BaseMessage");
    }
}
