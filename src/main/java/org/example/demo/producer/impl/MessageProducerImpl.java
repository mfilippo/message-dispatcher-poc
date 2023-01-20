package org.example.demo.producer.impl;

import org.example.demo.messages.BaseMessage;
import org.example.demo.producer.service.MessageProducer;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerImpl implements MessageProducer {

    @Override
    public BaseMessage produce() {
        throw new RuntimeException("Not implemented");
    }
}
