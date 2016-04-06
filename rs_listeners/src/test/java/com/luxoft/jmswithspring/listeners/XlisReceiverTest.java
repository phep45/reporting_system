package com.luxoft.jmswithspring.listeners;

import com.luxoft.jmswithspring.listener.XLISReceiver;
import com.luxoft.jmswithspring.listener.XlisEvent;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlisReceiverTest {

    @InjectMocks
    private XLISReceiver xlisReceiver;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void shouldReceiveAndPublish() {
        String test = "test";
        xlisReceiver.receiveMessage(test);

        verify(applicationEventPublisher).publishEvent(argThat(
                Matchers.allOf(Matchers.hasProperty("source", is(test)))));
    }

}
