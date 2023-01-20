package org.example.demo.producer.service;

import org.example.demo.messages.BaseMessage;

public interface MessageProducer {

    BaseMessage produce();
}
