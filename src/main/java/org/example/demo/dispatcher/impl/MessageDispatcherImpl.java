package org.example.demo.dispatcher.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.example.demo.dispatcher.service.MessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageDispatcherImpl<T> implements MessageDispatcher<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDispatcherImpl.class);

    private final Map<Class<? extends T>, Consumer<? extends T>> mappings = new HashMap<>();
    private Consumer<T> defaultCallback = null;

    @Override
    public <S extends T> void register(Class<S> type, Consumer<S> callback) {
        mappings.put(type, callback);
        LOGGER.info("Registered callback for class: {}", type);
    }

    @Override
    public void registerDefault(Consumer<T> callback) {
        defaultCallback = callback;
        LOGGER.info("Registered default callback");
    }

    @Override
    public void dispatch(T message) {
        if (mappings.containsKey(message.getClass())) {
            ((Consumer<T>) mappings.get(message.getClass())).accept(message);
        } else if (defaultCallback != null) {
            defaultCallback.accept(message);
        } else {
            LOGGER.warn("No callback found for class: {}", message.getClass());
        }
    }

}
