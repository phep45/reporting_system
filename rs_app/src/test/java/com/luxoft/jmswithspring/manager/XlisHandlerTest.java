package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.listener.XlisEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XlisHandlerTest {

    @InjectMocks
    private XlisHandler xlisHandler;

    @Mock
    private DataManager dataManager;

    private static final String INPUT = "some input";

    @Test
    public void shouldHandle() {
        XlisEvent xlisEvent = new XlisEvent(INPUT);
        xlisHandler.onApplicationEvent(xlisEvent);

        verify(dataManager).processXLIS(INPUT);
    }

}
