package com.luxoft.jmswithspring.camel.internalid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class InternalIdAutoUpdater {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final long MILLISECONDS = 1000L;

    private InternalId internalId;

    private Toolkit toolkit;
    private Timer timer;

    public InternalIdAutoUpdater(InternalId internalId) {
        this.internalId = internalId;
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
    }

    public void schedule(int seconds){
        timer.schedule(new InternalIdTask(), delay(seconds));
    }

    private long delay(int seconds) {
        return seconds * MILLISECONDS;
    }

    private class InternalIdTask extends TimerTask {
        @Override
        public void run() {
            toolkit.beep();
            internalId.updateInternalIdFile();
            timer.cancel();
        }
    }

    public void resetTimer() {
        timer.cancel();
    }

}
