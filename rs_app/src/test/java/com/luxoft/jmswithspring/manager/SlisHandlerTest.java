package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.listener.SlisEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlisHandlerTest {

    @InjectMocks
    private SlisHandler slisHandler;

    @Mock
    private DataManager dataManager;

    private static final String INPUT = "some input";

    @Test
    public void shouldHandle() {
        SlisEvent slisEvent = new SlisEvent(INPUT);
        slisHandler.onApplicationEvent(slisEvent);

        verify(dataManager).processSLIS(INPUT);
    }

}
