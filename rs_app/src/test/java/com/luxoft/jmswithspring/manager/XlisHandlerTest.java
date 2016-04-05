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

    @Test
    public void shouldHandle() {
        XlisEvent xlisEvent = new XlisEvent(anyString());
        xlisHandler.onApplicationEvent(xlisEvent);

        verify(dataManager).processXLIS(anyString());
    }

}
