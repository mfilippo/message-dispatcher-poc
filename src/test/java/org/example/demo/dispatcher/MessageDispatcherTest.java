package org.example.demo.dispatcher;

import org.example.demo.dispatcher.service.MessageDispatcher;
import org.example.demo.handler.service.MessageHandler;
import org.example.demo.messages.BaseMessage;
import org.example.demo.messages.MessageA;
import org.example.demo.messages.MessageB;
import org.example.demo.producer.service.MessageProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MessageDispatcherTest {

    @Autowired
    private MessageDispatcher<BaseMessage> messageDispatcher;

    @MockBean
    private MessageHandler messageHandler;

    @Mock
    private MessageProducer messageProducer;

    @Test
    public void testMessageAHandlerIsInvoked() {
        MessageA specificMessage = new MessageA();
        Mockito.when(messageProducer.produce()).thenReturn(specificMessage);

        messageDispatcher.register(MessageA.class, m -> messageHandler.handle(m));

        BaseMessage genericMessage = messageProducer.produce();
        messageDispatcher.dispatch(genericMessage);

        Mockito.verify(messageHandler).handle(specificMessage);
    }

    @Test
    public void testMessageBHandlerIsInvoked() {
        MessageB specificMessage = new MessageB();
        Mockito.when(messageProducer.produce()).thenReturn(specificMessage);

        messageDispatcher.register(MessageB.class, m -> messageHandler.handle(m));

        BaseMessage genericMessage = messageProducer.produce();
        messageDispatcher.dispatch(genericMessage);

        Mockito.verify(messageHandler).handle(specificMessage);
    }

    @Test
    public void testMessageAHandlerWithDefaultCallback() {
        MessageA specificMessage = new MessageA();
        Mockito.when(messageProducer.produce()).thenReturn(specificMessage);

        messageDispatcher.registerDefault(m -> messageHandler.handle(m));

        BaseMessage genericMessage = messageProducer.produce();
        messageDispatcher.dispatch(genericMessage);

        Mockito.verify(messageHandler).handle(genericMessage);
    }

    @Test
    public void testMessageAHandlerWithNoCallbacks() {
        MessageA specificMessage = new MessageA();
        Mockito.when(messageProducer.produce()).thenReturn(specificMessage);

        BaseMessage genericMessage = messageProducer.produce();
        messageDispatcher.dispatch(genericMessage);

        Mockito.verifyNoInteractions(messageHandler);
    }
}
