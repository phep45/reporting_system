package com.luxoft.jmswithspring.listeners;

import com.luxoft.jmswithspring.listener.SLISReceiver;
import com.luxoft.jmswithspring.listener.SlisEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlisReceiverTest {

    @InjectMocks
    private SLISReceiver slisReceiver;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void shouldReceiveAndPublish() {

        slisReceiver.receiveMessage(anyString());

        verify(applicationEventPublisher).publishEvent(new SlisEvent(anyString()));

    }

}
