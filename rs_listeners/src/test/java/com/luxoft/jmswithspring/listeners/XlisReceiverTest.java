package com.luxoft.jmswithspring.listeners;

import com.luxoft.jmswithspring.listener.XLISReceiver;
import com.luxoft.jmswithspring.listener.XlisEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlisReceiverTest {

    @InjectMocks
    private XLISReceiver xlisReceiver;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void shouldReceiveAndPublish() {
        xlisReceiver.receiveMessage(anyString());

        verify(applicationEventPublisher).publishEvent(new XlisEvent(anyString()));
    }

}
