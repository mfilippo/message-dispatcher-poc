package org.example.demo.dispatcher.service;

import java.util.function.Consumer;

public interface MessageDispatcher<T> {

    <S extends T> void register(Class<S> type, Consumer<S> callback);

    void registerDefault(Consumer<T> callback);

    void dispatch(T message);
}
