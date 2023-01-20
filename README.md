Message Dispatcher POC
----------------------

Based on: https://stackoverflow.com/questions/50690523/processing-an-object-depending-on-its-implementation-of-an-interface-in-java/50690649

Recently, I had to cope with a situation where legacy code constraints did not allow to use any classic design pattern.

The situation:
- We have a method that generates objects (e.g. messages) but their type cannot be known in advance
- Classes defining messages are available but cannot be modified (e.g. no extends, no implements, ...)
- We would like to avoid the `if instanceof` approach

The final solution consists of using a message dispatcher holding all the mappings between message classes and callback methods.
A message handler (can be more than one) defines the callback methods. The message dispatcher mappings must be created at startup
time.

The message dispatcher interface is defined as:

```java
public interface MessageDispatcher<T> {

    <S extends T> void register(Class<S> type, Consumer<S> callback);

    void registerDefault(Consumer<T> callback);

    void dispatch(T message);
}
```

where `T` is the base class of all the messages (could be `Object`).

A message handler for base class `BaseMessage` could be defined as:

```java
public interface MessageHandler {

    void handle(MessageA message);

    void handle(MessageB message);

    void handle(BaseMessage message);

}
```

In this case a message dispatcher for `BaseMessage` messages could be initialized as:

```java
MessageDispatcher<BaseMessage> messageDispatcher = new MessageDispatcherImpl<>();
messageDispatcher.register(MessageA.class, m -> messageHandler.handle(m));
messageDispatcher.register(MessageB.class, m -> messageHandler.handle(m));
messageDispatcher.registerDefault(m -> messageHandler.handle(m));
```

Then, every received message is dispatched the same way:

```java
BaseMessage message = messageProducer.produce();
messageDispatcher.dispatch(message);
```

This repository contains a full implementation example and a few tests to demonstrate the usage.

The example takes the form of a Spring Boot application.
